const formulario = document.getElementById("formAgendamento");

formulario.addEventListener("submit", async function(event){
    event.preventDefault();

    const data = document.getElementById("dataAgendamento").value;
    const hora = document.getElementById("horaAgendamento").value;
    const dataHoraAgendamento = data + "T" + hora;

    const agendamento = {
        tipoServico: document.getElementById("tipoServico").value,
        tapeceiro: document.getElementById("tapeceiro").value,
        dataHoraAgendamento: dataHoraAgendamento,
        cliente: document.getElementById("cliente").value,
        telefoneCliente: document.getElementById("telefoneCliente").value
    };

    console.log(agendamento);

    const mensagem = document.getElementById("mensagem");
    try{
        const resposta = await fetch("/agendamentos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(agendamento)
        });

        if (resposta.status === 202) {
            mensagem.textContent = "Agendamento realizado com sucesso!";
            formulario.reset();
        } else if (resposta.status === 409) {
            mensagem.textContent = "Esse horário já está ocupado. Escolha outro.";
        } else {
            mensagem.textContent = "Não foi possível agendar. Confira os dados e tente novamente.";
        }

    }catch (erro){
        mensagem.textContent = "Não foi possível se conectar, tente novamente mais tarde.";
    }
});