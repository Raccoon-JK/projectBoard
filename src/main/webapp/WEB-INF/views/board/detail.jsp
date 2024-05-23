<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <title>Tables</title>

    <!-- Custom fonts for this template -->
    <link
      href="../vendor/fontawesome-free/css/all.min.css"
      rel="stylesheet"
      type="text/css"
    />
    <link
      href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
      rel="stylesheet"
    />

    <!-- Custom styles for this template -->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet" />

    <!-- Custom styles for this page -->
    <link
      href="../vendor/datatables/dataTables.bootstrap4.min.css"
      rel="stylesheet"
    />   
  </head>

  <body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
      <!-- Sidebar -->
      <%@ include file="/WEB-INF/views/board/commonSideNav.jsp" %>
      <!-- End of Sidebar -->
      <!-- Content Wrapper -->
      <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">
          <!-- Topbar -->
          <%@ include file="/WEB-INF/views/board/commonNav.jsp" %>
          <!-- End of Topbar -->

          <!-- Begin Page Content -->
          <div class="container-fluid h-100">
            <!-- Page Heading -->
            <h1 class="h3 mb-2 text-gray-800">게시판</h1>

            <!-- DataTales Example -->
            <div class="card shadow mb-4 h-75">
              <div class="card-body">
                <!-- Basic Card Example -->
                <div class="card shadow mb-4 h-100">
                  <div class="card-header py-3">
                    <h6
                      class="m-0 font-weight-bold text-primary btn float-left"
                    >
                      ${board.boardTitle}
                    </h6>
                    <c:choose>
						<c:when test="${isloggedIn && board.userNo eq loginMember.userNo}">
                    <a href="/board/modify?boardNo=${board.boardNo}">
                      <button
                        type="button"
                        class="btn btn-primary btn float-right ml-1"
                      >
                        수정
                      </button>
                    </a>
                    <button
                      type="button"
                      class="btn btn-danger btn float-right"
                      onclick="deleteBoard('${board.boardNo}')"
                    >
                      삭제
                    </button>
                    	</c:when>
                    </c:choose>
                  </div>
                  <div
                    class="card-body navbar-nav-scroll"
                    style="height: 290px !important; white-space: pre;"
                  >${board.boardContent}</div>         
				  <div class="card-body fileUpLoad">
                 <label class="fileUpLoadBtn">파일</label>
                 <div id="fileName" class="fileName">
                 	
                 	<c:forEach var="file" items="${fileList}">
                    	<a href="/board/board_file/${file.changeName}">${file.fileName}</a>
                 	</c:forEach>
                 
                 </div>
               </div>
                      
                  <div class="card-footer">                 
                  <form action="#" id="replyForm" name="replyForm">                  	    
                    <ul id="commentDiv" style="max-height: 500px; overflow-y: scroll;overflow-x: hidden;">
                        
                        <c:forEach var="comment" items="${comments}">
                        	<c:choose>
	                    		<c:when test="${comment.commentStatus == 0}"> 
                        
                       <li data-no="${comment.commentNo}" data-name="${comment.userName}" data-parent="${comment.commentParents}" data-deep="${comment.deep}" data-userNo="${comment.userNo}" data-boardNo="${comment.boardNo}" data-content="${comment.commentContent}">
                        
                         <div class="commentDiv" style="padding-left:${(2 + 10 * comment.deep)}px">
                     <div class="commentHead">
                        <div class="commentHead1">
                     
                           <div class="commentName">${comment.userName}</div>
                           <div class="commentDate" ><fmt:formatDate value="${comment.commentDate}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
                     
                        </div>
                  		<c:if test="${isloggedIn}">
                        <div class="commentHead2">
                     		
                           <div class="commentReply" onclick="replyComment(event)">답글</div>
                        <c:if test="${isloggedIn && comment.userNo eq loginMember.userNo}">
                           <div class="commentModify" onclick="modifyComment(event)">수정</div>
                        </c:if>
                        <c:if test="${isloggedIn && board.userNo eq loginMember.userNo || comment.userNo eq loginMember.userNo}">
                           <div class="commentRemove" onclick="deleteComment('${comment.commentNo}', '${comment.boardNo}')">삭제</div>
                        </c:if>
                           <div class="commentCancle" style="display:none;" onclick="commentCancle(event)">취소</div>
                     
                        </div>
                  		</c:if>
                     </div>
                     <div class="comment">
                     
                     <p style="white-space: pre;">${comment.commentContent}</p>                     
                     
                     
                     </div>
                    
                    </div>
                          <hr class="sidebar-divider d-none d-md-block">
                       </li>     
                                         
	                       	</c:when>
		                       	<c:otherwise>
					                  <li data-no="${comment.commentNo}" data-name="${comment.userName}" data-parent="${comment.commentParents}" data-deep="${comment.deep}" data-userNo="${comment.userNo}" data-boardNo="${comment.boardNo}" data-content="${comment.commentContent}">
					                      <div class="commentDiv" style="padding-left:${(2 + 10 * comment.deep)}px">
					                      	<div class="comment">                    
							                    <p>삭제된 댓글입니다.</p>                     						                     
							                </div>
					                      </div>
					                      <hr class="sidebar-divider d-none d-md-block">
					                   </li> 	
			                    </c:otherwise>		                       	
	                      </c:choose>	                      
                       </c:forEach>
                                 						                                                            
                    </ul>
                   </form>
                   <c:if test="${isloggedIn}">
                    <form id="editModal" style="display: none;">
						    <div id="nonEditableDisplay"></div> <!-- 수정할 수 없는 부분 -->
						    <textarea cols="30" row="5" id="editModalTextarea" name="editModalTextarea" class="editModalTextarea" style="width: 90%;  white-space: pre;" placeholder="내용
                         "></textarea> <!-- 수정할 수 있는 부분 -->
						   <a href="#" onclick="modifyCommentSubmit(event,'${board.boardNo}')">
						    <button type="button">수정</button>
						    </a>
						    <a href="#" onclick="closeEditForm(event)">
						    <button type="button">닫기</button>
						    </a>						    
					</form>  
                  <form class="flex" id="commentForm" name="commentForm">
                      <input type="hidden" name="boardNo" value="${board.boardNo}">
                      <input type="hidden" name="userNo" value="${loginMember.userNo}">
                      <input type="hidden" name="commentParents" value="0">
    				  <input type="hidden" name="deep" value="0">
                       <textarea id="a3" class="commentContentss"cols="30" row="5" name="commentContent" class="form-control flex" style="width: 90%; white-space: pre;" placeholder="내용
                         "></textarea>
                       <a href="#" class="commentAdd flex" style="width: 9%" onclick="submitComment(event)">
                         <button type="button" class="btn btn-primary btn ml-1" style="margin-top: 0.75rem;width: 100%">등록</button>
                       </a>
                  </form>                  
                     </c:if>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- /.container-fluid -->
        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
          <div class="container my-auto">
            <div class="copyright text-center my-auto">
              <span>Copyright &copy; Your Website 2020</span>
            </div>
          </div>
        </footer>
        <!-- End of Footer -->
      </div>
      <!-- End of Content Wrapper -->
    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div
      class="modal fade"
      id="logoutModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
            <button
              class="close"
              type="button"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">×</span>
            </button>
          </div>
          <div class="modal-body">
            Select "Logout" below if you are ready to end your current session.
          </div>
          <div class="modal-footer">
            <button
              class="btn btn-secondary"
              type="button"
              data-dismiss="modal"
            >
              Cancel
            </button>
            <a class="btn btn-primary" href="login.html">Logout</a>
          </div>
        </div>
      </div>
    </div>
    
    <script>
    var loginMember = {
      userNo: '${loginMember.userNo}',
      userName : '${loginMember.userName}'
    }
    var isloggedIn = ${isloggedIn}
    var board = {
    	userNo: '${board.userNo}'
    }
    </script>

    <!-- Bootstrap core JavaScript-->
    <script src="../vendor/jquery/jquery.min.js"></script>
    <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="/js/demo/datatables-demo.js"></script>
    <script src="/js/utill/allAjax.js"></script>
    <script src="/js/board/board.js"></script>
    <script src="/js/board/comment.js"></script>
  </body>
</html>
