<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Gestione catalogo | Biblioteca online</title>
   <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/libro.css">
    <link rel="stylesheet" href="/biblioteca/css/admin.css">
    <script>
      $(document).ready(function(){
        $('#libroModal').on('show.bs.modal', function (event) {
          var button = $(event.relatedTarget); // Button that triggered the modal
          var titolo = button.data('titolo');
          var autore = button.data('autore');
          var editore = button.data('editore');
          var isbn = button.data('isbn');
          var anno = button.data('anno');
          var genere = button.data('genere');
          var classificazione = button.data('classificazione');
          var copie = button.data('copie');
          var id = button.data('id');

          var modal = $(this);
          modal.find('.titolo').val(titolo);
          modal.find('.autore').val(autore);
          modal.find('.editore').val(editore);
          modal.find('.isbn').val(isbn);
          modal.find('.anno').val(anno);
          modal.find('.genere').val(genere);
          modal.find('.classificazione').val(classificazione);
          modal.find('.copie').val(copie);
          if (button.data('valore')=="1") {
              modal.find('.aggiorna').attr('action', '/biblioteca/admin/catalogo/aggiungi');
              modal.find('.modal-title').text("Nuovo libro");
              modal.find('.libro').hide();
          }
           else if(button.data('valore')=="2") {
        	     modal.find('.libro').show();
              modal.find('.modal-title').text(titolo+" - "+autore);
              modal.find('.aggiorna').attr('action', '/biblioteca/admin/catalogo/aggiorna/'+id);
              modal.find('img').attr('src', '/biblioteca/img/books/'+isbn+'.jpg');
              modal.find('img').attr('alt', titolo);
            }
        })
      });
    </script>
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
        <h2 class="display-4">Gestione catalogo</h2>
      </div>

      <div class="layer-section center">
        <div class="m-2" style="float:right">
          <button type="button" class="btn btn-sm btn-primary" data-toggle="modal"
           data-target="#libroModal" data-valore="1">Nuovo libro +</button>
        </div>
        <div class="table-responsive">
          <table class="table">
            <thead>
              <tr>
                <th scope="col">id</th>
                <th scope="col">Titolo</th>
                <th scope="col">Autore</th>
                <th scope="col">ISBN</th>
                <th scope="col">Copie totali</th>
                <th scope="col">Modifica</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="catalogo" items="${mappa}">
              <tr>
                <td>${catalogo.key.libro.id}</td>
                <td>${catalogo.key.libro.titolo}</td>
                <td>${catalogo.key.libro.autore}</td>
                <td>${catalogo.key.libro.isbn}</td>
                <td>${catalogo.value}</td>
                <td>
                  <button type="button" class="btn btn-sm btn-success"
                  data-toggle="modal"
                  data-target="#libroModal"
                  data-id="${catalogo.key.libro.id}"
                  data-titolo="${catalogo.key.libro.titolo}"
                  data-autore="${catalogo.key.libro.autore}"
                  data-editore="${catalogo.key.libro.editore}"
                  data-anno="${catalogo.key.libro.annoPubblicazione}"
                  data-genere="${catalogo.key.libro.genere}"
                  data-isbn="${catalogo.key.libro.isbn}"
                  data-classificazione="${catalogo.key.classificazione}"
                  data-copie="${catalogo.value}"
                  data-valore="2"
                  >
                  Modifica
                </button>
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
      </div>



    </div>
  </div>

    <div class="modal fade bd-example-modal-lg" id="libroModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <form action="" method="post" class="aggiorna">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel"></h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body row justify-content-around">
            <div class="libro col-sm-6 col-lg-4">
              <img src="" alt="">
            </div>
            <div class="dettagli col-sm-6 col-lg-8">
                <div class="voce row justify-content-between">
                  <div class="col-sm-3">
                    <em><strong>Titolo:</strong></em>
                  </div>
                  <div class="col-sm-6">
                    <input type="text" name="titolo" class="form-control titolo">
                  </div>
                </div>
                <hr class="hrVoce">
                <div class="voce row justify-content-between">
                  <div class="">
                    <em><strong>Autore:</strong></em>
                  </div>
                  <div class="col-sm-6">
                    <input type="text" name="autore" class="form-control autore">
                  </div>
                </div>
                <hr class="hrVoce">
                <div class="voce row justify-content-between">
                  <div class="">
                    <em><strong>Editore:</strong></em>
                  </div>
                  <div class="col-sm-6">
                    <input type="text" name="editore" class="form-control editore">
                  </div>
                </div>
                <hr class="hrVoce">
                <div class="voce row justify-content-between">
                  <div class="">
                    <em><strong>Anno di pubblicazione:</strong></em>
                  </div>
                  <div class="col-sm-6">
                    <input type="text" name="anno" class="form-control anno">
                  </div>
                </div>
                <hr class="hrVoce">
                <div class="voce row justify-content-between">
                  <div class="">
                    <em><strong>Genere:</strong></em>
                  </div>
                  <div class="col-sm-6">
                    <input type="text" name="genere" class="form-control genere">
                  </div>
                </div>
                <hr class="hrVoce">
                <div class="voce row justify-content-between">
                  <div class="">
                    <em><strong>ISBN:</strong></em>
                  </div>
                  <div class="col-sm-6">
                    <input type="text" name="isbn" class="form-control isbn">
                  </div>
                </div>
                <hr class="hrVoce">
                <div class="voce row justify-content-between">
                  <div class="">
                    <em><strong>Classificazione:</strong></em>
                  </div>
                  <div class="col-sm-6">
                    <input type="text" name="classificazione" class="form-control classificazione">
                  </div>
                </div>
                <hr class="hrVoce">
                <div class="voce row justify-content-between">
                  <div class="">
                    <em><strong>Copie:</strong></em><br>
                  </div>
                  <div class="col-sm-6">
                    <input type="text" name="copie" class="form-control copie">
                  </div>
                </div>
                <hr class="hrVoce">

            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
            <button type="submit" class="btn btn-danger">Salva</button>
          </div>
        </form>
        </div>
      </div>
    </div>



 <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
