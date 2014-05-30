function toggleShowPassed() {
	var status = (document.getElementById("showPassedSwitch")).src;
	var current = status.indexOf('showPassedOff.png')  > 0 ? '': 'none';
	var elements = document.getElementsByClassName('levelLog2 levelStatus0');
	for(var i = 0; i < elements.length; i++) {
			elements[i].style.display = current;
    }
	var isOn = status.indexOf('showPassedOn.png')  > 0;
	(document.getElementById("showPassedSwitch")).src = isOn ? 'showPassedOff.png': 'showPassedOn.png';
}

function toggleTimeStamp() {
	var elements = document.getElementsByClassName('timeStamp');

	var status = (document.getElementById("timeStampSwitch")).src;
	var isOn = status.indexOf('timeStampOn.png')  > 0;
	if (!isOn) {
	    createCookie("timeStamp", "enabled", 365);
	} else {
	    eraseCookie("timeStamp");
	}
	var current = status.indexOf('timeStampOff.png')  > 0 ? 'inline': 'none';
    for(var i = 0; i < elements.length; i++) {
			elements[i].style.display = current;
    }
	(document.getElementById("timeStampSwitch")).src = isOn ? 'timeStampOff.png': 'timeStampOn.png';
}

function toggleElement(elementId, displayStyle)
{
    var current = getStyle(elementId, 'display');
    document.getElementById(elementId).style.display = (current == 'none' ? displayStyle : 'none');
}


function getStyle(elementId, property)
{
    var element = document.getElementById(elementId);
    return element.currentStyle ? element.currentStyle[property]
           : document.defaultView.getComputedStyle(element, null).getPropertyValue(property);
}


function toggle(toggleId)
{
    var toggle;
    if (document.getElementById)
    {
        toggle = document.getElementById(toggleId);
    }
    else if (document.all)
    {
        toggle = document.all[toggleId];
    }
    toggle.textContent = toggle.innerHTML == '\u25b6' ? '\u25bc' : '\u25b6';
}

function toggleElement_my(elementId, displayStyle)
{
    
	var elements = document.getElementsByTagName('div');
	
	var current = (document.getElementById("toggle-" + elementId)).innerHTML == '\u25b6' ? 'block': 'none';
	
	var exp = (current == 'block')? "__\\d+$": "__\\d";
	var regex = new RegExp('step-' + elementId + exp, "i")
    for(var i = 0; i < elements.length; i++) {
        if (elements[i].id.match(regex)) {
			elements[i].style.display = current;
        }
    }
}

function toggle_my(toggleId)
{
    toggleId = 'toggle-' + toggleId;
    var toggle;
    if (document.getElementById)
    {
        toggle = document.getElementById(toggleId);
    }
    else if (document.all)
    {
        toggle = document.all[toggleId];
    }
	if (toggle != null) {
		toggle.textContent = toggle.innerHTML == '\u25b6' ? '\u25bc' : '\u25b6';
	}
}

function checkCookie() {
    if (readCookie("timeStamp") == "enabled") {
        toggleTimeStamp();
    }
}

$(document).ready(function() {
    $("a[rel=screenshots]").fancybox({
        'transitionIn'		: 'none',
        'transitionOut'		: 'none',
        'titlePosition' 	: 'inside',
        'titleFormat'		: function(title, currentArray, currentIndex, currentOpts) {
            return '<span id="fancybox-title-over">' + (title.length ? ' &nbsp; ' + title : '') + '&nbsp;&nbsp;<font style=\'font-size:80%\'>' + (currentIndex + 1) + '/' + currentArray.length +  '</span>';
        }
    });

}  )

function createCookie(name,value,days) {

	if (days) {
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

function eraseCookie(name) {
	createCookie(name,"",-1);
}