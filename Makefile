# # Spécifiez le compilateur Java
# JAVAC = javac

# # Spécifiez les options du compilateur
# JFLAGS = -g

# # Spécifiez le dossier contenant les fichiers source Java
# SRCDIR = src

# # Spécifiez le dossier de destination pour les fichiers compilés
# BINDIR = bin

# # Obtenez tous les fichiers source Java dans le dossier source
# SOURCES = $(wildcard $(SRCDIR)/*.java)

# # Obtenez tous les fichiers compilés correspondants dans le dossier de destination
# CLASSES = $(patsubst $(SRCDIR)/%.java,$(BINDIR)/%.class,$(SOURCES))

# # Règle pour la cible par défaut (tous les fichiers compilés)
# all: $(CLASSES)

# # Règle pour compiler les fichiers source Java en fichiers .class
# $(BINDIR)/%.class: $(SRCDIR)/%.java
# 	$(JAVAC) $(JFLAGS) -d $(BINDIR) $<

# # Règle pour nettoyer les fichiers compilés
# clean:
# 	rm -rf $(BINDIR)/*.class

# # Règle pour exécuter le programme
# run:
# 	java -cp $(BINDIR) Main

# # Règle pour exécuter le programme avec des arguments
# run_args:
# 	java -cp $(BINDIR) MainClass arg1 arg2

# # Règle pour afficher l'aide
# help:
# 	@echo "Utilisation du Makefile :"
# 	@echo " make          - Compile tous les fichiers source Java"
# 	@echo " make clean    - Supprime tous les fichiers compilés"
# 	@echo " make run      - Exécute le programme"
# 	@echo " make run_args - Exécute le programme avec des arguments"
# 	@echo " make help     - Affiche cette aide"

# # Marquez les règles comme des cibles qui ne sont pas des noms de fichiers
# .PHONY: all clean run run_args help

all:
	javac *.java && java Main