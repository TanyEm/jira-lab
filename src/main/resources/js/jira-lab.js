
function delData(delId){
	
	AJS.$.ajax({
		url:"/jira/plugins/servlet/jira/lab?id="+delId,
		async: false,
		type: "DELETE",
		dataType: "text",
		success: function(data){
			
			
			AJS.$('#row-data-'+delId).remove();
		}
	});
}


AJS.$(document).ready(function(){	
	
	AJS.$("#add-button").click(function() {
		AJS.dialog2("#dialog-modal").show();
		AJS.$('#demo-range-always').datePicker({'overrideBrowserDefault': true});
	});
	
	AJS.$("#send-data-button").click(function(){
		AJS.$.ajax({
			url:"/jira/plugins/servlet/jira/lab",
			async: false,
			type: "POST",
			
			data: "string="+AJS.$("#string-to-send").val()+"&date="+AJS.$("#demo-range-always").val(),
			dataType: "html",
			success: function(result){
				
				if (result !== "EMPTY") {
					
					AJS.dialog2("#dialog-modal").hide();
					AJS.$("#table-body-with-data").append(result);
				} else {
					
					alert("Some fileds are empty");
				}
			}
		});
	});
});