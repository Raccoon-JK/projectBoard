function allAjax(url, type, data) {
    return new Promise((resolve, reject) => {
		
		let processData = true;
        let contentType = 'application/x-www-form-urlencoded; charset=UTF-8';
    
        
        if (type.toLowerCase() === 'post') {
            processData = false;
            contentType = false;
        } else if (type.toLowerCase() === 'get' && url !== '/overlapCheck') {
            data = null;
        }    
        
		
		 $.ajax({
	        url: url,
	        type: type,
	        data: data,
	        processData: processData,
	        contentType: contentType,
	        success: function(response) {			
	            resolve(response);
	        },
	        error: function(error) {
	            reject({error});
	        }
        });
    });
}