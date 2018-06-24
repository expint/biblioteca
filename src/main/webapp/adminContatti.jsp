<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Contatti ricevuti | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/admin.css">
  </head>
  <body>
    <jsp:include page="./risorse/structure/adminNavbar.jsp">
     <jsp:param value="${utente.nome}" name="nomeUtente"/>
     <jsp:param value="${msg.testo}" name="msgTesto"/>  
     <jsp:param value="${msg.tipo}" name="msgTipo"/>  
     <jsp:param value="${msg.visualizzato}" name="msgVisual"/>
    </jsp:include>
    <div class="container-fluid first-section center">
      <div class="layer-band center">
        <h2 class="display-4">Contatti ricevuti</h2>
      </div>

      <div class="layer-section center">
        <div class="table-responsive">
        <c:if test="${empty contatti}">
         <h3>Nessun contatto presente</h3><br>
         <a href="/biblioteca/admin">Torna alla dashboard</a>
        </c:if>
        <c:if test="${!empty contatti}">
                
          <table class="table">
            <thead>
              <tr>
                <th scope="col">id</th>
                <th scope="col">Nome</th>
                <th scope="col">Cognome</th>
                <th scope="col">Mail</th>
                <th scope="col">Oggetto</th>
                <th scope="col">Messaggio</th>
                <th scope="col">Data</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="contatto" items="${contatti}">
              <tr>
                <td>${contatto.id}</td>
                <td>${contatto.nome}</td>
                <td>${contatto.cognome}</td>
                <td>${contatto.email}</td>
                <td>${contatto.oggetto}</td>
                <td>${contatto.testo}</td>
                <td><fmt:formatDate value="${contatto.data}" pattern="dd/MM/yyyy" /></td>
            </tr>
            </c:forEach>
            
            
          </tbody>
        </table>
        
        <br><br>
        <nav aria-label="Page navigation example">
          <ul class="pagination justify-content-center">
            <li class="page-item ${prev?'':'disabled'}">
              <a class="page-link" href="?p=${pagina-1}">Previous</a>
            </li>
            <li class="page-item ${next?'':'disabled'}">
              <a class="page-link" href="?p=${pagina+1}">Next</a>
            </li>
          </ul>
        </nav>
        </c:if>
      </div>



    </div>
  </div>

  <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
