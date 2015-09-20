public String uploadUsingAutoIt(String object,String data) throws IOException
{
try {ProcessBuilder p = null;
List path_file=new ArrayList();
driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
String currDir = System.getProperty("user.dir");
String sep = System.getProperty("file.separator");
String temp[] = data.split(",");
String obj[]=temp[1].split(" ");
String path_exe = currDir + sep + "externalFiles" + sep + "uploadFiles"+ sep + temp[0].trim();
for(int i=0;i<obj.length;i++)
{
String path = currDir + sep + "externalFiles" + sep + "uploadFiles"+ sep + obj[i].trim();
path_file.add(path);

}
for(int k=0;k<path_file.size();k++){
p=new ProcessBuilder(path_exe,path_file.get(k), "Open");
}
p.start();
}
catch (Exception e) {
return Constants.KEYWORD_FAIL + " Unable to upload "
+ e.getLocalizedMessage();
}
return Constants.KEYWORD_PASS+ "File Uploaded sucessfully.";
}
