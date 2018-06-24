<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Contatti | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/login.css">
  </head>
  <body>
    <jsp:include page="./risorse/structure/navbar.jsp">
     <jsp:param value="${utente.nome}" name="nomeUtente"/>
     <jsp:param value="${utente.admin}" name="admin"/>
     <jsp:param value="${msg.testo}" name="msgTesto"/>  
     <jsp:param value="${msg.tipo}" name="msgTipo"/>  
     <jsp:param value="${msg.visualizzato}" name="msgVisual"/>
    </jsp:include>
    <div class="container-fluid first-section">
      <div class="layer-band center">
        <h2 class="display-4">Contatti</h2>
      </div>

      <div class="layer-section">
        <div class="login col-md-5 mx-auto">
          <h3>Scrivi un messaggio</h3>
         <form:form name="aggiungiContatto" method="POST" action="./aggiungiContatto" modelAttribute="contatto">
            
            <c:if test = "${utente.nome != null && utente.nome!= ''}">
             <form:hidden path="nome" value="${utente.nome}" />
             <form:hidden path="cognome" value="${utente.cognome}" />
             <form:hidden path="email" value="${utente.mail}" />
            </c:if>
            
            <c:if test = "${utente.nome == null || utente.nome == ''}">
             <div class="form-group">
              <label for="nome">Nome</label>
              <form:input path="nome" class="form-control" placeholder="Nome..." required="required" />
            </div>
            <div class="form-group">
              <label for="cognome">Cognome</label>
              <form:input path="cognome" class="form-control" placeholder="Cognome..." required="required" />
            </div>
            <div class="form-group">
              <label for="email">Email</label>
              <form:input path="email" class="form-control" placeholder="Mail..." required="required" />
            </div>
            </c:if>
            
            
            <div class="form-group">
              <label for="oggetto">Oggetto</label>
              <form:input path="oggetto" class="form-control" placeholder="Oggetto..." required="required" />
            </div>
            <div class="form-group">
              <label for="testo">Testo (max 250 caratteri)</label>
              <form:textarea path = "testo" class="form-control" rows = "5" required="required" />
            </div>

            <button type="submit" class="btn btn-primary btn-block" id="submitBtn" name="submitBtn">Invia</button>
          </form:form>
        </div>



      </div>
    </div>


    <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
