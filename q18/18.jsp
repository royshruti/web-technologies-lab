<% response.setContentType("text/event-stream"); response.setCharacterEncoding("UTF-8"); try { String s="<h1><strong>"
    +Math.floor(Math.random()*20)+ "</strong></h1>" ; out.print("data: "+s);
    String s1 = " <h1><strong>"+Math.floor(Math.random()*20)+"</strong></h1>";
    out.print("data: "+s1);
    out.print("\n\n");
    out.flush();
    Thread.sleep(5000);
    }catch(Exception e) {
    out.println(e);
    }

    %>