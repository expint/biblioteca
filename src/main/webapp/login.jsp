<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Accedi | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="css/login.css">
    <script>
      $(document).ready(function(){
        $('.signin').click(function(){
          $(".registrati").slideDown();
        });
      });
    </script>
  </head>
  <body>
    <jsp:include page="./risorse/structure/navbar.jsp">
     <jsp:param value="${msg.testo}" name="msgTesto"/>  
     <jsp:param value="${msg.tipo}" name="msgTipo"/>  
     <jsp:param value="${msg.visualizzato}" name="msgVisual"/>
    </jsp:include>
    <div class="container-fluid center first-section">
      <div class="layer-band center">
        <h2 class="display-4">Login</h2>
      </div>
				
		<div class="layer-section">
        <div class="login col-md-5 mx-auto">
          <h3>Accedi</h3>
          <form:form name="dologin" method="POST" action="./dologin" modelAttribute="utente">
            <div class="form-group">
              <label for="mail">Mail</label>
              <form:input path="mail" class="form-control" id="mail" placeholder="Email..."/>
            </div>
            <div class="form-group">
              <label for="password">Password</label>
              <form:password path="password" class="form-control" id="pswd" placeholder="Password..."/>
            </div>
            <button type="submit" class="btn btn-primary btn-block" id="submitBtn" name="submitBtn">Invia</button>
          </form:form>
          <hr>
          <div class="">
            Non sei ancora registrato?
          </div>
          <button type="button" class="btn btn-danger margin_20 signin" name="button">Registrati</button>
          <div class="registrati left">
             <form:form name="registrati" method="POST" action="./registrati" modelAttribute="utente">
              <div class="form-group">
                <label for="nome">Nome</label>
                <form:input path="nome" class="form-control" placeholder="Nome..." required="required" />
              </div>
              <div class="form-group">
                <label for="cognome">Cognome</label>
                <form:input path="cognome" class="form-control" placeholder="Cognome..." required="required" />
              </div>
              <div class="form-group">
                <label for="mail">Mail</label>
                <form:input path="mail" class="form-control" placeholder="Email..." required="required" />
              </div>
              <div class="form-group">
                <label for="pswd">Password</label>
                <form:password path="password" class="form-control" placeholder="Password..." required="required"/>
              </div>
              <button type="submit" class="btn btn-warning btn-block" name="regBtn">Richiedi registrazione</button>
            </form:form>
          </div>
        </div>



      </div>
    </div>


   <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
