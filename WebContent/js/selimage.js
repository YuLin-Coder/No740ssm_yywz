function selimage() {
	window
			.open(
					"saveimage.jsp",
					"",
					"toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,copyhistory=no,scrollbars=yes,width=500,height=340,top="
							+ (screen.availHeight - 240)
							/ 2
							+ ",left="
							+ (screen.availWidth - 400) / 2 + "");
}

function selfile() {
	window
			.open(
					"savefile.jsp",
					"",
					"toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,copyhistory=no,scrollbars=yes,width=500,height=340,top="
							+ (screen.availHeight - 240)
							/ 2
							+ ",left="
							+ (screen.availWidth - 400) / 2 + "");
}
