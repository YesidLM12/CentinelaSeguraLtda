document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const errorEmail = document.getElementById('errorusername');
    const errorPassword = document.getElementById('errorpassword');
    const boton = document.getElementById('loginButton');
    const togglePassword = document.getElementById('togglePassword');

    // Mostrar u ocultar la contraseña
    togglePassword.addEventListener('click', () => {
       const estabaOulta = passwordInput.type === 'password';
       passwordInput.type = estabaOulta ? 'text' : 'password';
       togglePassword.textContent = estabaOulta ? 'Ocultar' : 'Mostrar';
       togglePassword.setAttribute('aria-label', estabaOulta ? 'Ocultar contraseña' : 'Mostrar contraseña');
    });

    //Mostrar un mensaje junto a un campo y marcarlo como inválido
    function mostrarError(campo, elementoMensaje, mensaje) {
       elementoMensaje.textContent = mensaje;
       campo.setAttribute('aria-invalid', mensaje ? 'true' : 'false');
    }

    // Quitar el error mientras el usuario corrige el campo
    emailInput.addEventListener('input', () => {
       mostrarError(emailInput, errorEmail, '');
    });
    
    passwordInput.addEventListener('input', () => {
       mostrarError(passwordInput, errorPassword, '');
    });

    form.addEventListener('submit', (e) => {
       emailInput.value = emailInput.value.trim();
       let valido = true;

       if(!emailInput.value) {
          mostrarError(emailInput, errorEmail, 'El correo electrónico es obligatorio');
          valido = false;
       }

       if(!passwordInput.value) {
          mostrarError(passwordInput, errorPassword, 'La contraseña es obligatoria');
          valido = false;
       }

       if(!valido) {
          e.preventDefault();
          (username.value ? passwordInput : emailInput).focus();
          return;
       }

       // Evitar doble envio
       boton.disabled = true;
       boton.textContent = 'Ingresando...';
    });
   
});