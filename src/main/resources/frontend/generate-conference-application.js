var rest_url = "http://localhost:8099/ostis/application/generate";

/* Hash map custom implementation */
HashMap = function(){
  this._dict = [];
}
HashMap.prototype._get = function(key){
  for(var i=0, couplet; couplet = this._dict[i]; i++){
    if(couplet[0] === key){
      return couplet;
    }
  }
}
HashMap.prototype.put = function(key, value){
  var couplet = this._get(key);
  if(couplet){
    couplet[1] = value;
  }else{
    this._dict.push([key, value]);
  }
  return this; // for chaining
}
HashMap.prototype.get = function(key){
  var couplet = this._get(key);
  if(couplet){
    return couplet[1];
  }
}

/* Hash map custom implementation */

var loadingSpinnerHtml = '<div id="circleG" class="row"><div id="circleG_1" class="circleG"></div><div id="circleG_2" class="circleG"></div><div id="circleG_3" class="circleG"></div></div><p></p>'
var successfulMessage = '<div  id="successful-message"class="alert alert-success fade in"><strong>Success!</strong> Notification was sent in entried email.</div>';
var errorMessage = '<div id="error-message" class="alert alert-danger fade in"><strong>Internal Server Error!</strong> Use usual way to be applied for coneference.</div>';


var selectMap = new HashMap;

selectMap.put('report', 'доклад').
    put('publication', 'публикация').
    put('young_scientist_report_competition', 'конкурс научных докладов молодых учёных').
    put('young_scientist_software_competition', 'конкурс программных продуктов молодых учёных').
    put('none', 'не имею').
    put('candidate_sciences', 'кандидат наук').
    put('doctor_sciences', 'доктор наук').
    put('senior_researcher', 'старший научный сотрудник').
    put('docent', 'доцент').
    put('professor', 'профессор');
 
 $( document ).ready(function() {
 	sendRequestButtonListener();
 	clickGenerateApplication();
 });

 var sysIndf = '';

function sendRequestButtonListener() {

	$("#send-application").on("click",function(){

	if(!isValidRequiredInput()) {return;}

	startSpinner();

	new Promise(function (resolve, reject) {
	
		sysIndf = '';
	 
		retrieveSystemIndf($("#first-name").val(),
			$("#last-name").val(),$("#middle-name").val());


		setTimeout(() => {
				 resolve();
			 }, 2000);

		console.log(sysIndf);

		}).then(response => {
			var data = JSON.stringify(obtainParticipantData(sysIndf));
			console.log(data);
			$.ajax({
	  		type: "POST",
	  		contentType: "application/json",
	  		timeout: 15000,
	  		url: rest_url,
	  		data: data,
	  		success: function(resultData){
	  			console.log(resultData);
	  			stopSpinner();
	  			resultData = jQuery.parseJSON(resultData);
	  			if(resultData.status == "ok"){
	  				clearInputsForm();
	  				$("#application-form").before(successfulMessage);
	  			}else{
	  				$("#application-form").before(errorMessage);
	  			}
	  		},
	  		error: function(xhr, ajaxOptions, thrownError){
	  			console.log(xhr);
	  			console.log(ajaxOptions);
	  			console.log(thrownError);
	  			stopSpinner();
	  			$("#application-form").before(errorMessage);
	  		}});
	   });
	});
 }

 function isValidRequiredInput(){
 	if(
 		$("#last-name").val() &&
 		$("#first-name").val() &&
 		$("#academic-degree").val() &&
 		$("#academic-title").val() &&
 		$("#email").val() &&
 		isValidEmail($("#email").val()) &&
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

function isValidEmail(email) {
  var re = /[-._a-z0-9]+@(?:[a-z0-9][-a-z0-9]+\.)+[a-z]{2,6}$/;
  return re.test(email);
}

 function obtainParticipantData(){
 	var participant = {};
 	
 	participant.lastName = $("#last-name").val();
 	participant.firstName = $("#first-name").val();
 	participant.middleName = $("#middle-name").val();
 	participant.academicDegree = selectMap.get($("#academic-degree").val());
 	participant.academicTitle = selectMap.get($("#academic-title").val());
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
 	application.participationForm = selectMap.get($("#form-of-participation").val());
 	application.speaker = $("#speaker").val();
 	application.conferenceCompetition = selectMap.get($("[name='conference-competition']").val());
 	participant.application = application;

 	if(sysIndf){
 		participant.sysIndf = sysIndf;
 	}else{
 		participant.sysIndf = '';
 	}
 	

 	return participant;
 }

function startSpinner(){
	$(".alert").remove();
	if(!$("#circleG").length){
		$("#application-form").before(loadingSpinnerHtml);}
}

function stopSpinner(){
	var circleG = $("#circleG");

	if(circleG.length){
		circleG.remove();
	}
}


function retrieveSystemIndf(firstName, lastName, middleName){


	

	var mainIdtf = lastName.trim() + " " + firstName.trim();

	if(middleName){
		mainIdtf = mainIdtf + " " + middleName.trim();
	}

	var nrel_system_idtf_addr;

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

	   // search by Ivanov I. I. pattern
		if(!sysIndf){

			mainIdtf = lastName.trim() + " " + firstName.trim().charAt(0).toUpperCase() + ".";

			if(middleName){
				mainIdtf = mainIdtf + " " + middleName.trim().charAt(0).toUpperCase() + ".";
			}

			console.log(mainIdtf);

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

		}

}


function clickGenerateApplication(){
	$("#generate-application").on("click",function(){
		clearInputsForm();
		$(".alert").remove();
	})
}

function clearInputsForm(){
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
			stopSpinner();
}



