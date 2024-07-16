package example.com

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.*

fun Application.configureRouting() {
    routing {
        staticResources("static", "static")

        get("/") {
        call.respondRedirect("/static/index.html")
        }

        get("/api/eventos") {
            call.respond(eventos)
        }


        post("/api/eventos") {
                val newEvento = call.receive<Evento>()
                eventos.add(newEvento)
                call.respondText("Evento adicionado com sucesso", status = HttpStatusCode.Created)
        }

        get("/api/eventos/{eventoId}") {
            val eventoId = call.parameters["eventoId"]?.toIntOrNull()

            if (eventoId != null && eventoId >= 0) {
                val evento = findEventoById(eventos, eventoId)

                if (evento != null) {
                    call.respond(evento)
                } else {
                    call.respondText("Evento não encontrado", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("ID inválido", status = HttpStatusCode.BadRequest)
            }
        }

    }

}

