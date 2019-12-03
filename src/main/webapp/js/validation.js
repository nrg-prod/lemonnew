flag1=false;
flag2=false;
flag3=false;
flag4=false;

function branchSave(){
	try {
		var branchName=document.getElementById("center_content:brachform:name").value;
		var bonus=document.getElementById("center_content:brachform:bonus_input").value;
		var overtime=document.getElementById("center_content:brachform:overtime_input").value;
		var late=document.getElementById("center_content:brachform:late_input").value;
		if(branchName==null || branchName==""){
			document.getElementById("bname").innerHTML = "please enter the branch name";
		}else{
			document.getElementById("bname").innerHTML = " ";
			flag1=true;
		}
		if(bonus==null || bonus==""){
			document.getElementById("bonuss").innerHTML = "please enter the bonus";
		}else{
			document.getElementById("bonuss").innerHTML = " ";
			flag2=true;
		}
		if(overtime==null || overtime==""){
			document.getElementById("otime").innerHTML = "please enter the overtime";
		}else{
			document.getElementById("otime").innerHTML = " ";
			flag3=true;
		}
		if(late==null || late==""){
			document.getElementById("latemin").innerHTML = "please enter the bonus";
		}else{
			document.getElementById("latemin").innerHTML = " ";
			flag4=true;
		}
		
		if(flag1==true && flag2==true && flag3==true && flag4==true){
			document.getElementById("center_content:brachform:savebranch").click();
		}
	} catch (e) {
		alert(e);
	}
}

function branchExists(){
	document.getElementById("bname").innerHTML = "the branch name already exists";
}
PrimeFaces.widget.Dialog.prototype.applyFocus = function() {
	var firstInput = this.jq.find(':not(:submit):not(:button):input:visible:enabled:first');
	if(!firstInput.hasClass('hasDatepicker')) {
		  firstInput.focus();
	}
};