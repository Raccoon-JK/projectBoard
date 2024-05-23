var commentTextarea;
var cursorPosition;
var commentName;

function inputHandler() {
        var content = commentTextarea.value;

        if (!content.startsWith("@" + commentName + " ")) {
            commentTextarea.value = "@" + commentName + " " + content.substring(cursorPosition); // 강제 초기화
            commentTextarea.setSelectionRange(cursorPosition, cursorPosition); // 커서 위치 복구
        }
    }

function replyComment(event){
	var commentElement = event.target.closest('li');
	var commentForm = document.getElementById('commentForm');
	
	var commentNo = commentElement.getAttribute('data-no');
    commentName = commentElement.getAttribute('data-name');
    var commentDeep = parseInt(commentElement.getAttribute('data-deep'));
    
    /* 부모댓글 번호 가져오기 및 deep 올리기 */
    commentForm.querySelector('input[name="commentParents"]').value = commentNo;
    commentForm.querySelector('input[name="deep"]').value = commentDeep + 1;

    /* 부모 댓글 이름 가져오기 */
    commentTextarea = commentForm.querySelector('textarea[name="commentContent"]');
    commentTextarea.value = "@" + commentName + " ";
    commentTextarea.focus();

    /* 부모 이름 수정 막기 */
    cursorPosition = commentTextarea.selectionStart; // 커서 위치 저장
    
    commentTextarea.addEventListener('input', inputHandler);

     /* 취소 버튼 이벤트 처리 */
    var cancelButton = commentElement.querySelector('.commentCancle');
    cancelButton.style.display = 'block';
    btnDisplayController();
    cancelButton.addEventListener('click', function cancelHandler() {

        commentTextarea.value = "";
 
        commentForm.querySelector('input[name="commentParents"]').value = "0";
        commentForm.querySelector('input[name="deep"]').value = "0";
               
        cancelButton.style.display = 'none';
        btnDisplayController();
        /* 입력창 이벤트 핸들러 제거 */
        commentTextarea.removeEventListener('input', inputHandler);
        /* 취소 버튼 이벤트 핸들러 제거 */
        cancelButton.removeEventListener('click', cancelHandler);
    });
}

function btnDisplayController(){
	const btn = document.querySelectorAll('.commentHead2 > div:not(.commentCancle)');
	btn.forEach(btn => {
		if(btn.style.display !== 'none'){
		   	btn.style.display = 'none';
	    } else {
			btn.style.display = 'block';		
		}
	});
}

/* 댓글 등록 */
async function submitComment(event) {
    event.preventDefault();
    let areaVal = $("#a3").val().trim();
	if (checkBlank(areaVal)) {
    const form = document.getElementById('commentForm');
    const formData = new FormData(form);
    
    try{
		const allComments = await allAjax('/comments/comments', 'post', formData);
		
		if(allComments){
			console.log('댓글 등록 완료:', allComments);
			
			renderComments(allComments);
			
			/* 댓글 라인 맞추기 */
			$('#commentDiv li').each(function() {
			  var deep = $(this).data('deep');
			  var padding = (2 + 10 * deep) + 'px';
			  $(this).find('.commentDiv').css('padding-left', padding);
			});						
            
            form.reset(); // 댓글 폼 초기화
            var commentContent = document.querySelector("[name=commentContent]");             
            commentContent.removeEventListener('input', inputHandler);           
            
				var content = $(commentTextarea).val();
				if (content == "") {
				commentForm.querySelector('input[name="commentParents"]').value = "0";
        		commentForm.querySelector('input[name="deep"]').value = "0";
				}							
		} else {
			console.log('댓글 등록 실패:', allComments);
		}		
	} catch (error) {
		 console.error('에러 발생:', error);
		 alert("서버와 통신 중 문제가 발생했습니다.");
	}
  }
}	

function modifyComment(event) {
    const commentElement = event.target.closest('li');
    const commentContent = commentElement.querySelector('.comment > p').textContent;  

   	/* 공백 마지막 찾기 */
    const lastSpaceIndex = commentContent.lastIndexOf(' ');

    let nonEditablePart = '';  // 수정 불가한 부분
    let editablePart = '';    // 수정 가능한 부분

    if (lastSpaceIndex !== -1) {
        /* 마지막 공백을 기준으로 앞 부분은 '@아이디', 뒷부분은 내용 */
        nonEditablePart = commentContent.slice(0, lastSpaceIndex + 1); // 공백 포함
        editablePart = commentContent.slice(lastSpaceIndex + 1); // 공백 이후
    } else {
        /* 공백 없을때 처리 */
        editablePart = commentContent;
    }
	
	editModalClone(commentElement, nonEditablePart, editablePart);
}

	/* editModal 복제하기 */
function editModalClone(commentElement, nonEditablePart, editablePart){
    let editModal = document.getElementById('editModal').cloneNode(true);
    let textArea = editModal.querySelector('textarea[name="editModalTextarea"]');

    let nonEditableDisplay = editModal.querySelector('#nonEditableDisplay');
    nonEditableDisplay.textContent = nonEditablePart;
    textArea.value = editablePart;
    let commentDiv = commentElement.querySelector('.commentDiv');
    commentDiv.appendChild(editModal);
    editModal.style.display = 'block';
    textArea.focus();
    btnDisplayController();
}

function closeEditForm(event) {
	event.preventDefault();
    const editModal = document.getElementById('editModal');
     if (editModal) {
        editModal.remove();
        btnDisplayController();
     }
}

async function modifyCommentSubmit(event, boardNo) {
	event.preventDefault();
	let areaVal = $("#editModalTextarea").val().trim();
	if (checkBlank(areaVal)) {

    const editModal = document.getElementById('editModal');
    const editablePart  = editModal.querySelector('textarea[name="editModalTextarea"]').value;
    const nonEditablePart = editModal.querySelector('#nonEditableDisplay').textContent;
    
    const commentContent = nonEditablePart + editablePart;

    const commentNo = editModal.closest('li').dataset.no;
    
    const formData = new FormData();
    formData.append('commentNo', commentNo);
    formData.append('commentContent', commentContent);
    
    try{
		const data = await allAjax('/comments/modifyComment', 'post', formData);
		
		if (data > 0) {
			alert("수정 성공");
            fetchComments(boardNo);
	            
        } else {
          alert("수정 실패");
        }
		
	} catch (error) {
		 console.error('댓글 수정 오류:', error);
		 alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
	}
    }
}	

function checkBlank(areaVal){	
	if(!areaVal){
		alert('내용이 입력되지 않았습니다.');		
		return false;
	}		
	return true;
}

/* 댓글 조회 ajax용 */
function renderComments(allComments) {
			/* 기존 댓글 전체 지우기 */
            $('#commentDiv').empty();       
                 
            allComments.forEach(function(comment) {				
				var isCommentOwner = isloggedIn && comment.userNo == loginMember.userNo;
				var deleteOwner = isloggedIn && comment.userNo == loginMember.userNo || board.userNo == loginMember.userNo;
				
				if(comment.commentStatus == 0){
                var newComment = `
                    <li data-no="${comment.commentNo}" data-name="${comment.userName}" data-parent="${comment.commentParents}" data-deep="${comment.deep}" data-userNo="${comment.userNo}" data-boardNo="${comment.boardNo}" data-content="${comment.commentContent}">
                        <div class="commentDiv" style="padding-left: ${(2 + 10 * comment.deep)}px;">
                            <div class="commentHead">
                                <div class="commentHead1">
                                    <div class="commentName">${comment.userName}</div>
                                    <div class="commentDate">${comment.commentDate.slice(0, comment.commentDate.indexOf('T')) + ' ' + comment.commentDate.slice(11, 19)}</div>
                                </div>`;
                                
                            if(isloggedIn){
                               newComment += `
                                <div class="commentHead2">
                                    <div class="commentReply" onclick="replyComment(event)">답글</div>`;
							if(isCommentOwner){
								newComment += `
                                    <div class="commentModify" onclick="modifyComment(event)">수정</div>`;
							}
							if(deleteOwner){
								newComment += `
                                    <div class="commentRemove" onclick="deleteComment('${comment.commentNo}', '${comment.boardNo}')">삭제</div>`;
                            }
                                newComment += `
                                	<div class="commentCancle" style="display:none;" onclick="commentCancle(event)">취소</div>
                                </div>`;
							}
								 newComment += `
                            </div>
                            <div class="comment">
                                <p style="white-space: pre;">${comment.commentContent}</p>
                            </div>
                        </div>
                        <hr class="sidebar-divider d-none d-md-block">
                    </li>`;
                $('#commentDiv').append(newComment);
                } else{
					var newComment = `
						<li data-no="${comment.commentNo}" data-name="${comment.userName}" data-parent="${comment.commentParents}" data-deep="${comment.deep}" data-userNo="${comment.userNo}" data-boardNo="${comment.boardNo}" data-content="${comment.commentContent}">
					 		<div class="commentDiv" style="padding-left: ${(2 + 10 * comment.deep)}px;">
					  			<div class="comment">
                                	<p>삭제된 댓글입니다.</p>
                       			</div>
                        	</div>
                        		<hr class="sidebar-divider d-none d-md-block">
                    	</li>`;
                     $('#commentDiv').append(newComment);
				}             
            });
}

/* 댓글 삭제 */
async function deleteComment(commentNo, boardNo) {
        var confirmed = confirm("댓글을 삭제 하시겠습니까?");
        if (confirmed) {
			const formData = new FormData();
			formData.append('commentNo', commentNo);
			
			try{
				const data = await allAjax('/comments/commentsDelete', 'post', formData);
				 
				 if (data > 0) { 
                    alert("삭제되었습니다.");                      
                    fetchComments(boardNo);
                } else {
                    alert("삭제 실패하였습니다."); 
                }				
			} catch (error){
				alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
			}
		} else {
            console.log("사용자가 삭제를 취소했습니다.");
    }
}			

async function fetchComments(boardNo) {
	
	try{
		const allComments = await allAjax(`/comments/comments?boardNo=${boardNo}`, 'get', null);
		
		if(allComments){
			renderComments(allComments, boardNo);
		}
	} catch (error){
		console.error('에러 발생:', error);
		alert("에러 발생");
	}
}
