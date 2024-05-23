<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

    <title>SB Admin 2 - Register</title>

    <!-- Custom fonts for this template-->
    <link
      href="../vendor/fontawesome-free/css/all.min.css"
      rel="stylesheet"
      type="text/css"
    />
    <link
      href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
      rel="stylesheet"
    />

    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet" />
    
  </head>

  <body class="bg-gradient-primary">
    <div class="container">
      <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
          <!-- Nested Row within Card Body -->
          <div class="row">
            <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
            <div class="col-lg-7">
              <div class="p-5">
                <div class="text-center">
                  <h1 class="h4 text-gray-900 mb-4">회원가입</h1>
                </div>
                <form class="user" method="post" action="/member/membership" id="userJoinForm">
                  <div class="form-group">
                    <input
                      type="text" name="userName" data-value-type="USER_NAME"
                      class="form-control form-control-user" autofocus maxlength="10" id="userName" oninput="checkReg(event)"
                      placeholder="이름"
                    />
                    <span class="msg_box">${errorMsg.userName}</span>
                  </div>
                  <div class="form-group row">
                    <div class="col-sm-9 mb-3 mb-sm-0">
                      <input
                        type="text" name="email" data-value-type="EMAIL" data-use-alert="true"
                        class="form-control form-control-user" id="email" maxlength="50" oninput="checkReg(event)"
                        placeholder="이메일주소"
                      />                                    
                    </div>
                    <div class="col-sm-3">
                      <button id="emailCheckBtn" type="button"
                        class="btn btn-primary btn-user btn-block"
                      >
                        중복확인
                      </button>
                    </div>
                  </div>
                  <div class="form-group row">
                    <div class="col-sm-6 mb-3 mb-sm-0">
                      <input 
                        type="password" name="userPwd" id="password1" maxlength="15" data-use-alert="true"
                        class="form-control form-control-user passwords"
                        placeholder="비밀번호"
                      />
                    </div>
                    <div class="col-sm-6">
                      <input
                        type="password" name="userPwdCheck" id="password2" maxlength="15"
                        class="form-control form-control-user passwords"
                        placeholder="비밀번호 확인"
                      />
                    </div>
                  </div>
                  <div class="form-group">
                    <input
                      type="text" name="phone" id="phone" maxlength="13" oninput="checkReg(event)" data-use-alert="true"
                      class="form-control form-control-user"
                      placeholder="휴대폰번호"
                    />
                  </div>
                  <div class="form-group row">
                    <div class="col-sm-9 mb-3 mb-sm-0">
                      <input
                        type="text" name="address" id="address" maxlength="20" data-use-alert="true"
                        class="form-control form-control-user addressGroup"
                        placeholder="주소"
                      />
                    </div>
                    <div class="col-sm-3">
                      <button type="button" onclick="execDaumPostcode()"                   
                        class="btn btn-primary btn-user btn-block"
                      >주소찾기
                      </button>                     
                    </div>
                  </div>
                  <div class="form-group">
                    <input
                      type="text" name="addressDetail" id=addressDetail maxlength="50" data-use-alert="true"
                      class="form-control form-control-user addressGroup"
                      placeholder="상세주소"
                    />
                  </div>
                  <div class="form-group row">
                    <div class="col-sm-6 mb-3 mb-sm-0">
                      <input
                        type="text" name="postCode" id="postCode" maxlength="10" oninput="checkReg(event)" data-use-alert="true"
                        class="form-control form-control-user addressGroup"
                        placeholder="우편번호"
                      />
                    </div>
                    <div class="col-sm-6">
                      <input
                        type="text" name="note" id="note" maxlength="20"
                        class="form-control form-control-user"
                        placeholder="참고사항"
                      />
                    </div>
                  </div>

                  <button type="submit" id="joinBtn"
                  
                    class="btn btn-primary btn-user btn-block">
				  	Register Account
				  </button>
                </form>
                <hr />
                <div class="text-center">
                  <a class="small" href="/member/loginUser"
                    >Already have an account? Login!</a
                  >
                </div>
              </div>
            </div>
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
    
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="/js/utill/allAjax.js"></script>
    <script type="text/javascript" src="/js/member/join.js"></script>
  </body>
</html>