 
 $( document ).ready(function() {
 	sendRequestButtonListener()
 });

function sendRequestButtonListener() {
	var sendApplicationButtton = $("#send-application");

	sendApplicationButtton.on("click",function(){
		
		var data = JSON.stringify(obtainParticipantData());
		console.log(data);

		$.ajax({
  		type: "POST",
  		contentType: "application/json",
  		dataType: "json",
  		url: "http://localhost:8080/ostis/application/generate",
  		data: data,
  		success: function(){
  			console.log(" send !!!!");
  		},
  		error: function(xhr, ajaxOptions, thrownError){
  			console.log(xhr);
  			console.log(ajaxOptions);
  			console.log(thrownError);
  		}});
	})
 }

 function obtainParticipantData(){
 	var participant = {};
 	
 	participant.lastName = $("#surname").val();
 	participant.firstName = $("#name").val();
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
 	application.conferenceCompetition = $("#conference-competition").val();
 	participant.applicationList = [application];

 	return participant;
 }


