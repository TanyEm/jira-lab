function delData(delId){
			AJS.$.ajax({
					  url:"/jira/plugins/servlet/jira/lab?id="+delId,
					  async: false,
					  type: "DELETE",
					  dataType: "text",
					  headers: { "Accept": "*/*" },
					  success: function(data){
					  	alert("Selected row was removed");
					  }
			});

		}