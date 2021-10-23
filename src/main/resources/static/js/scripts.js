//login
function mostrarOcultarSenha(){
	
 var senha = document.getElementById("password");

  if(senha.type == "password"){
	
     senha.type = "text";

}
	else{
		
		if(senha.type == "text"){
			
  			  senha.type = "password";
	
	}
}

}
//Tema Dark
function mudaTema() { 
	document.body.classList.toggle("dark");
	} 
//timeline
function limitarCaracter() {
  var texto = document.getElementById("texto").value;

  if (texto.length > 8 && texto.length < 240) {
	document.getElementById("botao").disabled = false;
  } else {
    document.getElementById("botao").disabled = true;
  }
}
function mensagem(){
	
   document.getElementById('alerSugestaoCadastrada').classList.remove('hide')
	}
function confirmado(){
	alert("Sua nota foi cadastrada")
}

//Formulario
//cpf
function validaCpf() {
  var cpf = document.getElementById("cpf").value;

if(cpf =="11111111111" || cpf=="22222222222" || cpf=="33333333333" || cpf=="44444444444" || cpf=="55555555555" || cpf=="66666666666" || cpf=="77777777777" ||
 cpf=="88888888888" || cpf=="99999999999"){
	document.getElementById('alertCpf4').classList.remove('hide')
	return false;
}
if (cpf.length!=11){
	document.getElementById('alertCpf').classList.remove('hide')

	return false;
}

  var Soma;
  var Resto;
  Soma = 0;
  

  for (i=1; i<=9; i++) Soma = Soma + parseInt(cpf.substring(i-1, i)) * (11 - i);
  Resto = (Soma * 10) % 11;

    if ((Resto == 10) || (Resto == 11))  Resto = 0;
    if (Resto != parseInt(cpf.substring(9, 10)) ){
   document.getElementById('alertCpf2').classList.remove('hide')
    return false;
}
  Soma = 0;
    for (i = 1; i <= 10; i++) Soma = Soma + parseInt(cpf.substring(i-1, i)) * (12 - i);
    Resto = (Soma * 10) % 11;

    if ((Resto == 10) || (Resto == 11))  Resto = 0;
    if (Resto != parseInt(cpf.substring(10, 11) ) ){
   document.getElementById('alertCpf2').classList.remove('hide')
    return false;
  }  
 else{
	document.getElementById('alertCpf3').classList.remove('hide')
	return true;
}
}
//Pendentes
function sugestaoAprovada(){
	alert("Sugestao aprovada para classificação dos colaboradores!")
}
//mais votadas
function enviarAdm(){
	alert("Enviado para Administrador!")
}
function arquivar(){
	alert("Sugestão arquivada!")
}
