
var loadingSpinnerHtml = '<div id="circleG" class="row"><div id="circleG_1" class="circleG"></div><div id="circleG_2" class="circleG"></div><div id="circleG_3" class="circleG"></div></div><p></p>'
var successfulMessage = '<div  id="successful-message"class="alert alert-success fade in"><strong>Success!</strong> Notification was sent in entried email.</div>';
var errorMessage = '<div id="error-message" class="alert alert-danger fade in"><strong>Internal Server Error!</strong> Use usual way to be applied for coneference.</div>';

 
 $( document ).ready(function() {
 	sendRequestButtonListener();
 	clearInputsForm();
 });

function sendRequestButtonListener() {

	$("#send-application").on("click",function(){

	if(!isValidRequiredInput()) {return;}

	startSpinner();
 
	retrieveSystemIndf($("#first-name").val(),
		$("#last-name").val(),$("#middle-name").val());
			
		var data = JSON.stringify(obtainParticipantData());
		console.log(data);

		$.ajax({
  		type: "POST",
  		contentType: "application/json",
  		dataType: "json",
  		url: "http://localhost:8080/ostis/application/generate",
  		data: data,
  		success: function(){
  			stopSpinner();
  			$("#application-form").before(successfulMessage);
  		},
  		error: function(xhr, ajaxOptions, thrownError){
  			console.log(xhr);
  			console.log(ajaxOptions);
  			console.log(thrownError);
  			stopSpinner();
  			$("#application-form").before(errorMessage);
  		}});
	});
 }

 function isValidRequiredInput(){
 	if(
 		$("#last-name").val() &&
 		$("#first-name").val() &&
 		$("#academic-degree").val() &&
 		$("#academic-title").val() &&
 		$("#email").val() &&
 		$("#country").val() &&
 		$("#city").val() &&
 		$("#organization").val() &&
 		$("#position").val() &&
 		$("#name-of-article").val() &&
 		$("#article-authors").val() &&
 		$("#form-of-participation").val() &&
 		$("#speaker").val() &&
 		$("[name='conference-competition']").prop("checked")
 	  ) { return true;}

 	return false;
 }

 function obtainParticipantData(){
 	var participant = {};
 	
 	participant.lastName = $("#last-name").val();
 	participant.firstName = $("#first-name").val();
 	participant.middleName = $("#middle-name").val();
 	participant.academicDegree = $("#academic-degree").val();
 	participant.academicTitle = $("#academic-title").val();
 	participant.email = $("#email").val();

 	var workingPlace = {}
 	workingPlace.country = $("#country").val();
 	workingPlace.city = $("#city").val();
 	workingPlace.organization = $("#organization").val();
    workingPlace.position = $("#position").val();

 	participant.workingPlace = workingPlace;

 	var application = {};

 	application.nameOfArticle = $("#name-of-article").val();
 	application.authorsOfArticle = $("#article-authors").val();
 	application.participationForm = $("#form-of-participation").val();
 	application.speaker = $("#speaker").val();
 	application.conferenceCompetition = $("[name='conference-competition']").val();
 	participant.applicationList = [application];

 	return participant;
 }

function startSpinner(){
	$(".alert").remove();
	if(!$("#circleG").length){
		$("#application-form").before(loadingSpinnerHtml);}
}

function stopSpinner(){
	$("#circleG").remove();
}


function retrieveSystemIndf(firstName, lastName, middleName){

	var mainIdtf = lastName.trim() + " " + firstName.trim();

	if(middleName){
		mainIdtf = mainIdtf + " " + middleName.trim();
	}

	var nrel_system_idtf_addr;
	var sysIndf;

	SCWeb.core.Server.resolveScAddr(['nrel_system_identifier'], function (keynodes) {
      	nrel_system_idtf_addr = keynodes['nrel_system_identifier'];});

	SCWeb.core.Server.findIdentifiersSubStr(mainIdtf, function(data) {     
		
		if(data.main[0]){
			var sysIndfAddr = data.main[0][0];
		  	window.sctpClient.iterate_elements(SctpIteratorType.SCTP_ITERATOR_5F_A_A_A_F, [
		        sysIndfAddr,
		        sc_type_arc_common | sc_type_const,
		        sc_type_link,
		        sc_type_arc_pos_const_perm,
		        nrel_system_idtf_addr]).
		      done(function(identifiers){  
		        window.sctpClient.get_link_content(identifiers[0][2],'string').done(function(content){
		          console.log('System indf: ' + content);
		          sysIndf = content;
		         });        
		 	});
	    }
	});

	return sysIndf;
}


function clearInputsForm(){

	$("#generate-application").on("click",function(){
			$("#last-name").val('');
	 		$("#first-name").val('');
	 		$("#middle-name").val('');
	 		$("#email").val('');
	 		$("#country").val('');
	 		$("#city").val('');
	 		$("#organization").val('');
	 		$("#position").val('');
	 		$("#name-of-article").val('');
	 		$("#article-authors").val('');
	 		$("#speaker").val('');
	 		$("#form-of-participation").val('report');
	 		$("#academic-degree").val('none');
	 		$("#academic-title").val('none');
	 		$("[name='conference-competition']").prop("checked", false);
			$(".alert").remove();
			stopSpinner();
	 	})
}
