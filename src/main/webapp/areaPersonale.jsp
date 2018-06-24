<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Area personale | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/areapersonale.css">
    <link rel="stylesheet" href="/biblioteca/css/catalogo.css">
    <script>
    $(document).ready(function(){
      $(".accordion .btn").click(function(){
        $(this).siblings().slideToggle();

        if ($(this).val()=="0") {
          $(this).css("border-radius", "5px 5px 0 0");
          $(this).val("1");
          $(this).find("span").html("&#9650;");
        }
        else {
          $(this).css("border-radius", "5px");
          $(this).val("0");
          $(this).find("span").html("&#9660;");
        }
      });
      
      var libri = $("#libri");
      libri.on('click', '.copertina', function(e){
        window.location.href = "./catalogo/"+e.target.dataset.isbn;
        // console.log(e.target.dataset.isbn);
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
        <h2 class="display-4">Area personale</h2>
      </div>

      <div class="layer-section">
        <div class="card accordion col-lg-8 mx-auto">
          <button class="btn btn-lg btn-block btn-success" value="0">Prenotazioni attive<span class="badge accordionBadge">&#9660;</span></button>
          <div class="card-body" id="libri">
          <c:if test="${empty attive}">
            <p class="lead">Nessuna prenotazione attiva</p>
          </c:if>
            <c:forEach var="attive" items="${attive}">
        <div class="libro">
          <img src="img/books/${attive.catalogo.libro.isbn}.jpg" class="copertina" alt="${attive.catalogo.libro.titolo} - ${attive.catalogo.libro.autore}" title="${attive.catalogo.libro.titolo} - ${attive.catalogo.libro.autore}" data-isbn="${attive.catalogo.libro.isbn}">
								<div class="action">
											<form:form name="restituisci" method="POST"
												action="./restituisci/${attive.catalogo.id}"
												modelAttribute="utente">
												<button type="submit" class="btn btn-outline-success btn-sm"
													name="button">Restituisci</button>
											</form:form>
								</div>
						</div>
        </c:forEach>
          </div>
        </div>
        <div class="card accordion col-lg-8 mx-auto">
          <button class="btn btn-lg btn-block btn-danger" value="0">Tutte le prenotazioni<span class="badge accordionBadge">&#9660;</span></button>
          <div class="card-body">
            <div class="row">
              <div class="col-2">
                <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                
                  <c:forEach var="mappa" items="${mappa}">
                   <a class="nav-link center" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-${mappa.key}" role="tab" aria-controls="v-pills-profile" aria-selected="false">${mappa.key}</a>
                  </c:forEach>
                </div>
              </div>
              <div class="col-10 contenuto">
                <c:if test="${empty mappa}">
                  <p class="lead">Nessuna prenotazione recente</p>
                </c:if>
                <c:if test="${!empty mappa}">
                <div class="tab-content" id="v-pills-tabContent">
                <div class="tab-pane fade show active" role="tabpanel">Clicca sulle date</div>
                  <c:forEach var="mappa" items="${mappa}">
                  <div class="tab-pane fade" id="v-pills-${mappa.key}" role="tabpanel" aria-labelledby="v-pills-${mappa.key}-tab">
                    <div class="table-responsive">
                      <table class="table table-striped table-sm">
                        <thead>
                          <tr>
                            <th scope="col">Titolo</th>
                            <th scope="col">Autore</th>
                            <th scope="col">Preso in prestito il</th>
                            <th scope="col">Riconsegnato il</th>
                          </tr>
                        </thead>
                        <tbody>
                  <c:forEach var="prenotazione" items="${mappa.value}">
                          <tr>
                            <td>${prenotazione.catalogo.libro.titolo}</td>
                            <td>${prenotazione.catalogo.libro.autore}</td>
                            <td><fmt:formatDate value="${prenotazione.dataPrenotazione}" pattern="dd/MM/yyyy" /></td>
                            <td>
               <c:if
																	test="${prenotazione.dataConsegna==null || prenotazione.dataConsegna==''}">
																	<form:form name="restituisci" method="POST"
																		action="/biblioteca/restituisci/${prenotazione.catalogo.id}"
																		modelAttribute="utente">
																		<button type="submit"
																			class="btn btn-success btn-sm prenota"
																			name="button">Restituisci</button>
																	</form:form>
																</c:if>
																<c:if test="${prenotazione.dataConsegna!=null && prenotazione.dataConsegna!=''}">
																<fmt:formatDate value="${prenotazione.dataConsegna}" pattern="dd/MM/yyyy" />
                </c:if>
                            </td>
                          </tr>
                        </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                        </c:forEach>
                </div>
                </c:if>
              </div>
            </div>
          </div>
        </div>
        <div class="card accordion col-lg-8 mx-auto">
          <button class="btn btn-lg btn-block btn-primary" value="0">La mia card<span class="badge accordionBadge">&#9660;</span></button>
          <div class="card-body center">
            <jsp:include page="./risorse/structure/card.jsp">
								     <jsp:param value="${utente.nome}" name="nomeUtente"/>
								     <jsp:param value="${utente.cognome}" name="cognomeUtente"/>
								     <jsp:param value="${utente.id}" name="idUtente"/>  
								    </jsp:include>
								    <a href="./risorse/structure/card.jsp?nomeUtente=${utente.nome}&cognomeUtente=${utente.cognome}&idUtente=${utente.id}" target="_blank">Apri in una nuova pagina</a>
          </div>
        </div>

      </div>
    </div>


   <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
