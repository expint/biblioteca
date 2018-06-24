<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>${catalogo.libro.titolo} - ${catalogo.libro.autore} | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/libro.css">
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
        <h2 class="display-4">${catalogo.libro.titolo}</h2>
      </div>

      <div class="layer-section" id="libri">
        <div class="row justify-content-around">
          <div class="libro">
            <img src="/biblioteca/img/books/${catalogo.libro.isbn}.jpg" class="copertina" alt="titolo - autore" title="autore1 - titolo1" data-isbn="${catalogo.libro.isbn}">
          </div>
          <div class="dettagli">
            <ul class="list-group">
              <li class="list-group-item"><em><strong>Titolo:</strong></em> ${catalogo.libro.titolo}</li>
              <li class="list-group-item"><em><strong>Autore:</strong></em> ${catalogo.libro.autore}</li>
              <li class="list-group-item"><em><strong>Editore:</strong></em> ${catalogo.libro.editore}</li>
              <li class="list-group-item"><em><strong>Anno di pubblicazione:</strong></em> ${catalogo.libro.annoPubblicazione}</li>
              <li class="list-group-item"><em><strong>Genere:</strong></em> ${catalogo.libro.genere}</li>
              <li class="list-group-item"><em><strong>ISBN:</strong></em> ${catalogo.libro.isbn}</li>
              <li class="list-group-item"><em><strong>Classificazione:</strong></em> ${catalogo.classificazione}</li>

            </ul>
          </div>
          <div class="actions center">
            <h4>Copie in catalogo</h4>
            <div class="numero">${totali}</div>
            <h4>Copie disponibili</h4>
            <div class="numero">${disponibili}</div>
            <c:if test = "${utente.nome!=null && utente.nome!='' && disponibili > 0}">
               <form:form name="prenota" method="POST" action="/biblioteca/prenota/${catalogo.libro.isbn}" modelAttribute="utente">
                <button type="submit" class="btn btn-success btn-block btn-lg prenota" name="button" >Prenota</button>
               </form:form>
            </c:if> 
            <c:if test = "${utente.nome==null && disponibili > 0}">
             <h4 class="disponibile">Disponibile</h4>
            </c:if>
            <c:if test = "${disponibili <= 0}">
             <h4 class="nondisponibile">Disponibile a breve</h4>
            </c:if>
            
            <c:if test = "${idCopiaDaRestituire!=null && idCopiaDaRestituire>0}">
             <form:form name="restituisci" method="POST" action="/biblioteca/restituisci/${idCopiaDaRestituire}" modelAttribute="utente">
                <button type="submit" class="btn btn-danger btn-block btn-lg prenota" name="button" >Restituisci</button>
               </form:form>
            </c:if>
          </div>
        </div>

      </div>
    </div>


   <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
