let joinBtnClicked = false;

async function overlapCheck(data) {
    let isUseable = false;
    if (data.valueType) {
		try{
			const result = await allAjax('/overlapCheck', 'get', data);
			
			if (result == 0) {
                isUseable = true;
            }
		} catch (error){
			if (!joinBtnClicked) {
                alert("에러");
        	}
		}
	}
		return isUseable;
}


/* 정규식 모음 */
const regexPatterns = {
    userName: /^[A-Za-z0-9]{1,10}$/,
    email: /^[a-zA-Z._]+@[a-zA-Z_]+\.[a-zA-Z]{1,3}$/,
    userPwd: /^(?=.*[a-zA-Z0-9])(?=.*[!@#$%^*+=-]).{8,15}$/,
    phone: /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/,
    userNameAllowed: /[^0-9a-zA-Z]/g,
    phoneAllowed: /[^0-9]/g,
    postCodeAllowed: /[^0-9]/g,
    emailAllowed: /[^a-zA-Z._@]/g
};

function generateEventHandlers() {
    const eventHandlers = {};

    $("input").not("[name='note']").each(function() {
        const fieldName = $(this).attr("name");

        eventHandlers[fieldName] = {};

        eventHandlers[fieldName].event1 = function(inputElement, msgBox) {
            if (!joinBtnClicked) {
                if (inputElement.data("useAlert")) {
                    alert(`${$(inputElement).attr("placeholder")}을(를) 입력해주세요`);
                    inputElement.focus();
                } else {
                    msgBox.text(`${$(inputElement).attr("placeholder")}을(를) 입력해주세요`);
                }
            }
        };

        eventHandlers[fieldName].event2 = function(inputElement, msgBox) {
            if (!joinBtnClicked) {
                if (inputElement.data("useAlert")) {
                    alert(`유효하지 않은 ${$(inputElement).attr("placeholder")} 형식입니다.`);
                    inputElement.focus();
                } else {
                    msgBox.text(`사용 불가한 ${$(inputElement).attr("placeholder")} 입니다.`);
                }
            }
        };

        eventHandlers[fieldName].event3 = function(inputElement, msgBox) {
            if (!joinBtnClicked) {
                if (inputElement.data("useAlert")) {
                    alert(`사용 가능한 ${$(inputElement).attr("placeholder")}입니다.`);
                } else {
                    msgBox.text(`사용 가능한 ${$(inputElement).attr("placeholder")} 입니다.`);
                }
            }
        };

        eventHandlers[fieldName].event4 = function(inputElement, msgBox) {
            if (!joinBtnClicked) {
                if (inputElement.data("useAlert")) {
                    alert(`사용 불가능한 ${$(inputElement).attr("placeholder")}입니다.`);
                } else {
                    msgBox.text(`사용 불가능한 ${$(inputElement).attr("placeholder")} 입니다.`);
                }
            }
        };
    });

    return eventHandlers;
}

const eventHandlers = generateEventHandlers();

function validateInput(value, regexPattern) {
    return regexPattern.test(value);
}

async function validateAndCheckOverlap(inputElement) {
    const name = inputElement.attr("name");
    const value = inputElement.val().replace(" ", "");
    const msgBox = inputElement.siblings(".msg_box");
    const regexPattern = regexPatterns[name];
    const valueType = inputElement.data("value-type");
    const data = {
        valueType: valueType,
        value: value
    };
    let isValid = true;

    if (name === 'address' || name === 'addressDetail' || name === 'postCode') {
        if (!value) {
            eventHandlers[name].event1(inputElement, msgBox);
            isValid = false;
        }
    } else if (name === 'userPwdCheck') {
        const userPwd = $("input[name='userPwd']").val();
        if (value === userPwd) {
            if (!joinBtnClicked) {
                alert("비밀번호가 일치합니다.");
            }
        } else {
            if (!joinBtnClicked) {
                alert("비밀번호가 일치하지 않습니다.");
            }
            isValid = false;
        }
    } else if (!validateInput(value, regexPattern)) {
        eventHandlers[name].event2(inputElement, msgBox);
        isValid = false;
    } else if (!(name === 'userName' || name === 'email')) {
        eventHandlers[name].event3(inputElement, msgBox);
    } else if (await overlapCheck(data)) {
        eventHandlers[name].event3(inputElement, msgBox);
    } else {
        eventHandlers[name].event4(inputElement, msgBox);
        isValid = false;
    }

    return isValid;
}

$("input").not("[name='email'], [name='note']").change(function() {
    validateAndCheckOverlap($(this));
});

$("#emailCheckBtn").click(function() {
    validateAndCheckOverlap($("input[name='email']"));
});

function checkReg(event) {
    const del = event.target;
    const name = $(del).attr("name");
    const regexPattern = regexPatterns[name + 'Allowed'];
    if (regexPattern.test(del.value)) {
        del.value = del.value.replace(regexPattern, '');
    }
}

$("#joinBtn").click(async function(event) {
    event.preventDefault();

    joinBtnClicked = true;
    let allValid = true;

    for (let inputElement of $("input").not("[name='note']")) {
        const isValid = await validateAndCheckOverlap($(inputElement));
        if (!isValid) {
            allValid = false;         
            break;
        }
    }

    joinBtnClicked = false;

    if (!allValid) {
        alert("유효하지 않은 값이 있습니다.");
    } else {
        if (confirm("회원가입을 하시겠습니까?")) {
            alert("회원가입이 완료되었습니다.")
            $("#userJoinForm").off("submit").submit();        
        }else{
			alert("회원가입을 취소하였습니다.");
		}
    }
});

/* 주소찾기 */
function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("note").value = extraAddr;
                
                } else {
                    document.getElementById("note").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postCode').value = data.zonecode;
                document.getElementById("address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("addressDetail").focus();
            }
        }).open();
    }

