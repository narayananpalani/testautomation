	;MsgBox(0,"Parameters",$CmdLine[1]) ;Sending parameters through command line

	FileCopy("C:\W3C.png", "C:\"&$CmdLine[1]&".png") ;Make a duplicate copy of the file, as same file name is not accepted

	sleep(3000);delay for 3 seconds for the file to create

	WinWaitActive("#32770", "Choose File to Upload",10)

	WinFlash("Choose File to Upload","", 4, 500) ; Just to Flash the window

	ControlSetText("Choose File to Upload", "", "[CLASS:Edit; INSTANCE:1]", "C:\"&$CmdLine[1]&".png")

	ControlClick("Choose File to Upload", "","[CLASS:Button; INSTANCE:2]");