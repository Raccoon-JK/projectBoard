<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    
    <link href="../css/boardPageList.css" rel="stylesheet"/>
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
          <div class="container-fluid">
            <!-- Page Heading -->
            <h1 class="h3 mb-2 text-gray-800">게시판</h1>

            <!-- DataTales Example -->
            <div class="card shadow mb-4">
              <div class="card-body">
                <div class="table-responsive">
                  <table
                    class="table table-bordered"
                    id=""
                    width="100%"
                    cellspacing="0"
                  >
                    <colgroup>
                      <col width="20%" />
                      <col width="40%" />
                      <col width="30%" />
                      <col width="20%" />
                    </colgroup>

                    <thead>
                      <tr>
                        <th>닉네임</th>
                        <th>제목</th>
                        <th>날짜</th>
                        <th>댓글</th>
                      </tr>
                    </thead>
                    <tbody>
	                    <c:forEach var="b" items="${boards}">
<%-- 	                    	<c:choose> --%>
<%-- 	                    		<c:when test="${b.boardStatus == 0}">                   --%>
			                      <tr>
			                        <td>${b.userName}</td>
			                        <td><a href="/board/detail?boardNo=${b.boardNo}">${b.boardTitle}</a></td>
			                        <td>${b.createDate}</td>
			                        <td>${b.commentCount}</td>
<!-- 			                      </tr> -->
<%--                       		    </c:when> --%>
<%-- 		                      		<c:otherwise> --%>
<!-- 				                     	<tr> -->
<!-- 				                      		<td colspan="4">삭제 된 게시물입니다.</td> -->
<!-- 				                    	</tr> -->
<%-- 		                      		</c:otherwise>	 --%>
<%--                       		</c:choose> --%>
                    	</c:forEach>
                    </tbody>
                  </table>
                  
                  <div class="pageInfo_wrap" >
			        <div class="pageInfo_area">
			        	<ul id="pageInfo" class="pageInfo">
			        	
			       		<!-- 이전페이지 버튼 -->
		                <c:if test="${page.prev}">
		                    <li class="pageInfo_btn previous"><a href="${page.startPage-1}">Previous</a></li>
		                </c:if> 
			        
			        	<!-- 각 번호 페이지 버튼 -->
		                <c:forEach var="num" begin="${page.startPage}" end="${page.endPage}">
		                   <li class="pageInfo_btn ${page.cri.pageNum == num ? "activePage":"" }"><a href="${num}">${num}</a></li>
		                </c:forEach>
		                
		                <!-- 다음페이지 버튼 -->
		                <c:if test="${page.next}"> 
		                    <li class="pageInfo_btn next"><a href="${page.endPage + 1 }">Next</a></li>
		                </c:if> 
		                </ul>		 
			        </div>
			      </div>
			      <form id="moveForm" method="get">			      
			        <input type="hidden" name="pageNum" value="${page.cri.pageNum}">
			        <input type="hidden" name="amount" value="${page.cri.amount}">    
			      </form>
			            
			          <c:choose>
						<c:when test="${isloggedIn}">
			                  <a href="/board/write"
			                    ><button
			                      type="button"
			                      class="btn btn-primary btn float-right"
			                    >
			                      게시글 작성
			                    </button></a
			                  >
                 		 </c:when>
                 		  <c:otherwise>               	
                 		                   		  	  
                 		  	<a href="#" onclick="confirmLogin()"
			                    ><button
			                      type="button"
			                      class="btn btn-primary btn float-right"		                      
			                    >
			                      게시글 작성
			                    </button></a
			                  >
                 		  </c:otherwise>
                  	</c:choose>
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
            <a class="btn btn-primary" href="/board/tables">Logout</a>
          </div>
        </div>
      </div>
    </div>
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
    <script src="/js/board/board.js"></script>
  </body>
</html>