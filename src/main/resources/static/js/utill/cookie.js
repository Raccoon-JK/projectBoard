$(function(){
    fnInit();
});

//// 폼 확인
//function frm_check(){
//    saveid();
//}

// 초기화 함수
function fnInit(){
    var cookieid = getCookie("saveid");
    console.log("cookieid :" + cookieid);

    // 쿠키에 값이 있으면 체크박스를 체크하고 id에 값 넣기
    if(cookieid !== ""){
        $("input#saveId").prop("checked", true);
        $('#logId').val(cookieid);
    }
}

// 쿠키 설정
function setCookie(name, value, expiredays){
    var todayDate = new Date();

    if (expiredays > todayDate) {
        document.cookie = name + "=" + escape(value) + "; path=/; expires=" + expiredays.toGMTString() + ";";  // 쿠키 경로와 만료일 설정 수정
    } else {
        document.cookie = name + "=" + escape(value) + "; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT;";  // 이미 만료된 날짜 설정
    }
}

// 쿠키 가져오기
function getCookie(name){
    var search = name + "=";
    var offset = document.cookie.indexOf(search);
    
    if (offset !== -1) {  // 'offset'의 값이 유효한지 확인
        offset += search.length;
        var end = document.cookie.indexOf(";", offset);
        
        if (end === -1) {
            end = document.cookie.length;  // 세미콜론이 없을 경우 쿠키 문자열의 끝까지 가져오기
        }
        
        return unescape(document.cookie.substring(offset, end));  // 쿠키 값을 반환
    }
    return "";  // 쿠키가 없는 경우 빈 문자열 반환
}

// 체크박스에 따라 쿠키를 설정 또는 삭제
function saveid(){
    var expdate = new Date();

    if ($("#saveId").is(":checked")) {
        // 30일 후를 만료 날짜로 설정
        expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);
        setCookie("saveid", $("#logId").val(), expdate);
        console.log(expdate);
    } else {
        // 이미 만료된 날짜로 쿠키 설정 (즉시 만료)
        expdate.setTime(expdate.getTime() - 1000 * 3600 * 24 * 30); 
        setCookie("saveid", $("#logId").val(), expdate);
        console.log(expdate);
    }
}