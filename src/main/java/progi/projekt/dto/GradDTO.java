
package progi.projekt.dto;

import progi.projekt.model.Grad;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

    public class GradDTO {
        private UUID id;
        private String naziv;


        public GradDTO(Grad grad) {
            this.id = grad.getId();
            this.naziv = grad.getNaziv();


        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getNaziv() {
            return naziv;
        }

        public void setNaziv(String naziv) {
            this.naziv = naziv;
        }

    }


