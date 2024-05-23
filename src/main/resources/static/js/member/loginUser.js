function confirmLogout(){
	let confirmed = confirm("로그아웃 하시겠습니까?");
	
	if(confirmed){
		var form = document.createElement("form"); 
	    form.method = "post";
	    form.action = "/member/logout";
	    document.body.appendChild(form);
	    form.submit();
	}
}

function confirmLogin(){
	let confirmed = confirm("로그인 하시겠습니까?");
	if(confirmed){
		window.location.href = "/member/loginUser";
	}			
}

/* 로그인 */
async function submitLoginForm(event){
	event.preventDefault();
	 
	const form = document.getElementById("loginBtn");
	const formData = new FormData(form);
	 	
	try {
    	const data = await allAjax('/member/loginUser', 'post', formData);

	    if (data == 1) {
	        alert("로그인되었습니다.");
	        location.href = "/board/tables";
	    } else {
	        alert("이메일 혹은 비밀번호가 실패하였습니다.");
	    }
	    	saveid();
    } catch (error) {
        console.error("로그인 실패:", error);
        alert("서버와 통신 중 문제가 발생했습니다.");
    }
}