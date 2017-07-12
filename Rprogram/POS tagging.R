options(java.parameters = "- Xmx3000m")
library(rJava)
library(NLP)
library(openNLP)
library(data.table)
tagPOS <-  function(x) {
  s <- as.String(x)
  sent_token_annotator = Maxent_Sent_Token_Annotator()
  word_token_annotator = Maxent_Word_Token_Annotator()
  a2 = annotate(s, list(sent_token_annotator, word_token_annotator))
  pos_tag_annotator = Maxent_POS_Tag_Annotator()
  a3 = annotate(s, pos_tag_annotator, a2)
  a3w = subset(a3, type == "word")
  POStags = unlist(lapply(a3w$features, `[[`, "POS"))
  gc()
  return(paste(POStags,collapse = " "))
}

sentence <- "This is a short sentence consisting of some nouns, verbs, and adjectives."
tagPOS(sentence, language = "en")




txt <- c("This is a short tagging example, by John Doe.",
         "Too bad OpenNLP is so slow on large texts.")

extractPOS <- function(x, thisPOSregex) {
  x <- as.String(x)
  wordAnnotation <- annotate(x, list(Maxent_Sent_Token_Annotator(), Maxent_Word_Token_Annotator()))
  POSAnnotation <- annotate(x, Maxent_POS_Tag_Annotator(), wordAnnotation)
  POSwords <- subset(POSAnnotation, type == "word")
  tags <- sapply(POSwords$features, '[[', "POS")
  thisPOSindex <- grep(thisPOSregex, tags)
  tokenizedAndTagged <- sprintf("%s/%s", x[POSwords][thisPOSindex], tags[thisPOSindex])
  untokenizedAndTagged <- paste(tokenizedAndTagged, collapse = " ")
  untokenizedAndTagged
}

lapply(txt, extractPOS, "VB")


-XX:-UseGCOverheadLimit
library(NLP)
library(tm)
library(openNLP)
library(graph)
# --- FUNCTIONS
tagPOS <-  function(x, ...) {
  s <- as.String(x)
  word_token_annotator <- Maxent_Word_Token_Annotator()
  a2 <- Annotation(1L, "sentence", 1L, nchar(s))
  a2 <- annotate(s, word_token_annotator, a2)
  a3 <- annotate(s, Maxent_POS_Tag_Annotator(), a2)
  a3w <- a3[a3$type == "word"]
  POStags <- unlist(lapply(a3w$features, `[[`, "POS"))
  POStagged <- paste(sprintf("%s/%s", s[a3w], POStags), collapse = " ")
  list(POStagged = POStagged, POStags = POStags)
}
###### illustrate usage of tagPOS
str <- "this is a the first sentence."
tagged_str <-  tagPOS(str)
tagged_str