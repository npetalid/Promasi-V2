
	function changeLanguage(selection){
		//alert(document.location.href);
		document.location.href = "../" + selection + "/index.html";
		//if(selection == "zh_CN"){
		//	document.location.href = "../index.html";
		//} else {
		//	document.location.href = "./" + selection + "/index.html";
		//}
		//alert(document.location.href);
	}

	var request=null;


	function include(url, func){
		if(window.XMLHttpRequest){
			request=new XMLHttpRequest();
		}
		else if(window.ActiveXObject){
			try{
				request=new ActiveXObject("Msxml2.XMLHTTP");
			}catch(e){
				try{
					request=new ActiveXObject("Microsoft.XMLHTTP")
				}catch(e){
					request=false;
				}
			}	
		};
		if(request){
			request.onreadystatechange=func;
			request.open("GET",url,false);
			request.send(null);
		}
		//alert();
	}


	function includeTo(target){
		var display = document.getElementById(target);
		if(request.readyState==4){
			//alert("synchronized include html " + target + " file");
			var str=request.responseText;
			display.innerHTML=str;
		}
	}

	//synchronized include html head file
	function includeHead(){

		includeTo("head");
	}

	//synchronized include html copyright file
	function includeCopyright(){
		includeTo("copyright");
	}