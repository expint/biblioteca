$(document).ready(function(){
  $(".menu").click(function(){
    $(".aside-screen").animate({'width': 'toggle'});
    $(".aside-dark-all").toggle();
  });

  $(".aside-dark-all").click(function(){
    $(this).toggle();
    $(".aside-screen").animate({'width': 'toggle'});
  });
  $(".close-menu").click(function(){
    $(".aside-dark-all").toggle();
    $(".aside-screen").animate({'width': 'toggle'});
  });
});
