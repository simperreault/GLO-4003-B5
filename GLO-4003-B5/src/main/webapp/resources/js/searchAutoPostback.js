function post_to_url(path, method) {
	method = method || "post";

	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", path);


	var hiddenField = document.createElement("input");
	hiddenField.setAttribute("type", "hidden");
	hiddenField.setAttribute("name", "sport");
	var e = document.getElementById("sportList");
	var strUser = e.options[e.selectedIndex].text;
	if (strUser != "--- Choisir ---")
	{hiddenField.setAttribute("value", strUser);}
	form.appendChild(hiddenField);


	var hiddenField2 = document.createElement("input");
	hiddenField2.setAttribute("type", "hidden");
	hiddenField2.setAttribute("name", "team");
	var e = document.getElementById("teamList");
	var strUser = e.options[e.selectedIndex].text;
	if (strUser != "--- Choisir ---")
	{hiddenField2.setAttribute("value", strUser);}
	form.appendChild(hiddenField2);

	var hiddenField3 = document.createElement("input");
	hiddenField3.setAttribute("type", "hidden");
	hiddenField3.setAttribute("name", "days");
	var e = document.getElementById("dateList");
	var strUser = e.options[e.selectedIndex].value;
	if (strUser != "--- Choisir ---")
	{hiddenField3.setAttribute("value", strUser);}
	form.appendChild(hiddenField3);


	document.body.appendChild(form);
	form.submit();
}

function post_add_qte(path, method) {
	method = method || "post";

	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", path);
	

	var hiddenField = document.createElement("input");
	hiddenField.setAttribute("type", "hidden");
	hiddenField.setAttribute("name", "amount");
	var e = document.getElementById("amount");
	var strUser = e.value;
	hiddenField.setAttribute("value", strUser);
	form.appendChild(hiddenField);
	
	var hiddenField2 = document.createElement("input");
	 hiddenField2.setAttribute("type", "hidden");
	 hiddenField2.setAttribute("name", "ticketId");
	 var e = document.getElementById("ticketId");
	 var strUser = e.value;
	 hiddenField2.setAttribute("value", strUser);
	 form.appendChild(hiddenField2);
	 
	 var hiddenField3 = document.createElement("input");
	 hiddenField3.setAttribute("type", "hidden");
	 hiddenField3.setAttribute("name", "eventId");
	 var e = document.getElementById("eventId");
	 var strUser = e.value;
	 hiddenField3.setAttribute("value", strUser);
	 form.appendChild(hiddenField3);
	
	document.body.appendChild(form);
	form.submit();
}

//Add tickets to the basket (create form to handle that stuff; they will be searched)
function AddTickets(path, idToGetNbTickets, ticketLikeThisOne)
{
	var strNbToBuy = document.getElementById(idToGetNbTickets).value;
	
	if ( !(strNbToBuy % 1 === 0) /*isNotInt*/ )
	{
		return;
	}
	
	var form = document.createElement("form");
	form.setAttribute("method", "post");
	form.setAttribute("action", path);


	var hiddenField = document.createElement("input");
	hiddenField.setAttribute("type", "hidden");
	hiddenField.setAttribute("name", "nbSimilarTickets");
	hiddenField.setAttribute("value", strNbToBuy);
	form.appendChild(hiddenField);


	var hiddenField2 = document.createElement("input");
	hiddenField2.setAttribute("type", "hidden");
	hiddenField2.setAttribute("name", "ticketId");
	hiddenField2.setAttribute("value", ticketLikeThisOne);
	form.appendChild(hiddenField2);


	document.body.appendChild(form);
	form.submit();
}