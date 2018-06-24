<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Catalogo | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="css/catalogo.css">
    <script>
    $(document).ready(function(){
      var libri = $("#libri");
      libri.on('click', '.copertina', function(e){
        window.location.href = "./catalogo/"+e.target.dataset.isbn;
        // console.log(e.target.dataset.isbn);
      });
      $("#more").click(function(){
        $("#advancedSearch").slideToggle();
        var other = "Ricerca semplice";
        $(this).text($(this).text()==other? "Ricerca avanzata" : other);
      });
    });
    </script>
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
        <h2 class="display-4">Catalogo</h2>
      </div>

      <div class="layer-section">
        <div class="row justify-content-around">
          <h2>Cerca</h2>
          <div class="campiRicerca col-sm-6">
          <form action="./catalogo" method="GET">
            <div class="input-group input-group-lg">
              <input type="text" id="titolo" name="titolo" class="form-control" placeholder="Inserisci il titolo di un libro" aria-label="Titolo del libro da cercare" aria-describedby="basic-addon2">
              <div class="input-group-append">
                <button class="btn btn-success" id="titoloBtn" type="submit">Cerca per titolo</button>
              </div>
            </div>
            </form>
            <div id="advancedSearch">
              oppure
              <form action="./catalogo" method="GET">
              <div class="input-group">
                <input type="text" id="autore" name="autore" class="form-control" placeholder="Inserisci l'autore di un libro" aria-label="Autore del libro da cercare" aria-describedby="basic-addon2">
                <div class="input-group-append">
                  <button class="btn btn-success" id="autoreBtn" type="submit">Cerca per autore</button>
                </div>
              </div>
              </form>
              oppure
              <form action="./catalogo" method="GET">
              <div class="input-group">
                <select class="custom-select" name="genere" id="genere" onchange="this.form.submit();" aria-label="Genere del libro da cercare">
                  <option selected>Scegli il genere...</option>
                  <option value="italiana">Narrativa italiana</option>
                  <option value="straniera">Narrativa straniera</option>
                  <option value="Horror">Horror</option>
                  <option value="Fantascienza">Fantascienza</option>
                  <option value="Psicologia">Psicologia</option>
                  <option value="Gialli">Gialli</option>
                  <option value="Thriller">Thriller</option>
                  <option value="diritto">Economia e diritto</option>
                  <option value="ragazzi">Bambini e ragazzi</option>
                </select>
                <div class="input-group-append">
                  <button class="btn btn-success" id="genereBtn" type="submit">Cerca per genere</button>
                </div>
              </div>
              </form>
              oppure
              <form action="./catalogo" method="GET">
              <div class="input-group">
                <input type="text" name="isbn" class="form-control" id="isbn" placeholder="Inserisci l'ISBN di un libro" aria-label="ISBN del libro da cercare" aria-describedby="basic-addon2">
                <div class="input-group-append">
                  <button class="btn btn-success" id="isbnBtn" type="submit">Cerca per ISBN</button>
                </div>
              </div>
              </form>
            </div>
          </div>
          <button class="btn btn-warning h-100" type="button" id="more">Ricerca avanzata</button>
        </div>

      </div>

      <div class="layer-section center" id="libri">
        <h3 class="left">Clicca sulla copertina per vedere i dettagli</h3>
        <c:forEach var="catalogo" items="${mappa}">
        <div class="libro">
          <img src="img/books/${catalogo.key.libro.isbn}.jpg" class="copertina" alt="${catalogo.key.libro.titolo} - ${catalogo.key.libro.autore}" title="${catalogo.key.libro.titolo} - ${catalogo.key.libro.autore}" data-isbn="${catalogo.key.libro.isbn}">
          <div class="action">
            <c:if test = "${catalogo.value > 0}">
              <span>Disponibile</span>
              <c:if test = "${utente.nome!=null && utente.nome!=''}">
              <div style="float:right">
		             <form:form name="prenota" method="POST" action="./prenota/${catalogo.key.libro.isbn}" modelAttribute="utente">
		              <button type="submit" class="btn btn-outline-success btn-sm" name="button" >Prenota</button>
		             </form:form>
		             </div>
              </c:if> 
            </c:if> 
            <c:if test = "${catalogo.value == 0}">
             <span style="color:red">Disponibile a breve</span>
            </c:if>
          </div>
        </div>
        </c:forEach>


        <br><br>
        <nav aria-label="Page navigation example">
          <ul class="pagination justify-content-center">
            <li class="page-item ${prev?'':'disabled'}">
              <a class="page-link" href="?${search}&p=${pagina-1}">Previous</a>
            </li>
            <li class="page-item ${next?'':'disabled'}">
              <a class="page-link" href="?${search}&p=${pagina+1}">Next</a>
            </li>
          </ul>
        </nav>

      </div>
    </div>


    <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
