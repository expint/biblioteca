<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Prenotazioni attive | Biblioteca online</title>
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
        <h2 class="display-4">Prenotazioni attive</h2>
      </div>

      <div class="layer-section center">
        <div class="table-responsive">
        <c:if test="${empty prenotazioni}">
         <h3>Nessuna prenotazione attiva presente</h3><br>
         <a href="/biblioteca/admin">Torna alla dashboard</a>
        </c:if>
        <c:if test="${!empty prenotazioni}">
                
          <table class="table">
            <thead>
              <tr>
                <th scope="col">id</th>
                <th scope="col">Utente</th>
                <th scope="col">Copia</th>
                <th scope="col">Data prestito</th>
                <th scope="col">Data scadenza</th>
                <th scope="col">Azioni</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="prenotazione" items="${prenotazioni}">
              <tr>
                <td>${prenotazione.id}</td>
                <td><a href="/biblioteca/admin/prenotazioni/${prenotazione.utente.id}">${prenotazione.utente.nome} ${prenotazione.utente.cognome}</a></td>
                <td>${prenotazione.catalogo.libro.titolo} - ${prenotazione.catalogo.libro.autore}</td>
                <td><fmt:formatDate value="${prenotazione.dataPrenotazione}" pattern="dd/MM/yyyy" /></td>
                <td><fmt:formatDate value="${prenotazione.dataScadenza}" pattern="dd/MM/yyyy" /></td>
                <td>
                <c:if test="${prenotazione.stato=='in corso'}">
                 <form action="/biblioteca/admin/prenotazioni/chiudi/${prenotazione.id}" method="post">
                    <button type="submit" class="btn btn-sm btn-outline-danger chiudi"  name="chiudiBtn" value="0">Chiudi prenotazione</button>
                  </form>
                </c:if>
                </td>
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
