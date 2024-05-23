/* 메인 게시판 리스트 넘기기 */
$(".pageInfo a").on("click", function(e){
    e.preventDefault();
    
	let moveForm = $("#moveForm");
    moveForm.find("input[name='pageNum']").val($(this).attr("href"));
    moveForm.attr("action", "/board/tables");
    moveForm.submit();  
              
});

/* 게시글 작성 */
async function submitForm() {
	let boardTitle = $("#boardTitle").val().trim();
	let boardContent = $("#boardContent").val().trim();
	if (checkBlank(boardTitle, boardContent)) {
	    if (confirm("작성을 하시겠습니까?")) {
	        const form = document.getElementById("writeForm");
	        const formData = new FormData(form);
			
			try{
				const response = await allAjax(form.action, 'post', formData);
				
				if (response && response.success) {
                    alert("저장되었습니다.");
                    window.location.href = "/board/tables";
                } else {
                    alert("저장 실패.");
                }					
			} catch (error){
				console.error("Error:", error);
	            alert("서버와 통신 중 문제가 발생했습니다.");
			}
		} else{
			alert("작성을 취소하였습니다.");
		}
	}
}	

/* 게시글 삭제 */
async function deleteBoard(boardNo) {
    var confirmed = confirm("게시글을 삭제 하시겠습니까?");
    if (confirmed) {
		const formData = new FormData();
		formData.append('boardNo', boardNo);
		
		try{
			const data = await allAjax('/deleteBoard', 'post', formData);
			
			if (data > 0) {
                alert("삭제되었습니다.");
                location.href = "/board/tables";
            } else {
                alert("삭제 실패하였습니다.");
            }			
		} catch (error){
			console.error("Error:", error);
            alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
		}
	} else {
		alert("삭제를 취소하였습니다.")
        console.log("사용자가 삭제를 취소했습니다.");
    }
}

/* 게시글 수정 */
async function boardUpdate() {
	let boardTitle = $("#a1").val().trim();
	let boardContent = $("#a3").val().trim();
	if (checkBlank(boardTitle, boardContent)) {
        var confirmed = confirm("게시글을 수정 하시겠습니까?");     
        
        if (confirmed) {        
	        const form = document.getElementById("updateForm");
	        const formData = new FormData(form);
			
	        try{
				const data = await allAjax('/board/modify', 'post', formData);
				
				if (data > 0) { 
	                alert("수정되었습니다.");
	                location.href = "/board/tables";
	            } else {
	                alert("수정 실패하였습니다."); 
	            }			
			} catch (error) {
				console.error("Error:", error);
				alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
			}
		} else {
			alert("수정을 취소하였습니다.");
		}
	}
}	       

/* 파일 수정 */    
function removeFileElement(fileElement) {
	fileElement.style.display = 'none';
	var hiddenInput = document.getElementById("deletedFiles");
	var fileNo = fileElement.dataset.fileNo; // UPLOADFILE_NO 값 가져오기           
		if (hiddenInput.value) {
				hiddenInput.value += ',' + fileNo; // 삭제한 파일 넘버 넣기(식별자)
			} else {
	            hiddenInput.value = fileNo; // 처음값 넣기
	        }
}

/* 빈값 체크 */
function checkBlank(boardTitle, boardContent){
	if(!boardTitle){
		alert('제목이 입력되지 않았습니다.');		
		return false;
	}
	
	if(!boardContent){
		alert('내용이 입력되지 않았습니다.');		
		return false;
	}		
	return true;
}