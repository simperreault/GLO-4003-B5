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
	hiddenField.setAttribute("name", "sport");
	var e = document.getElementById("amount");
	var strUser = e.value;
	hiddenField.setAttribute("value", strUser);
	form.appendChild(hiddenField);
	
	document.body.appendChild(form);
	form.submit();
}

function toggle(id) {
	if( document.getElementById(id).style.display=='none' ){
		document.getElementById(id).style.display = '';
	}else{
		document.getElementById(id).style.display = 'none';
	}
}