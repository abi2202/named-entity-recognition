

require(plyr)
require(stringr)
require(tm)
s <- 'seldom Coronary angiography holds abnormal a central role in the diagnosis of coronary heart disease. We studied temporal trends in referral patterns 2000-09.We identified 156 496 first-time coronary angiographies in nationwide registries. Trends were analyzed in 2-year intervals. Numbers of acute and elective procedures increased. Mean age increased from 61.8 to 63.9 years and the proportion of females increased from 33 to 37%). An increase in the number of patients with prior chronic heart failure, cerebrovascular disease, diabetes, and arrhythmias  was observed. The proportion of acute patients examined the same day as hospitalized increased from 56.6 to 83.1%. Odds ratios (95% confidence interval) for treatment with statins, RAS-inhibitors, and acetylsalicylic acid at the time of coronary angiography increased.'

# we got a vector of sentences. plyr will handle a list
# or a vector as an "l" for us
# we want a simple array ("a") of scores back, so we use 
# "l" + "a" + "ply" = "laply":

neg = c("not","abnormal","seldom","rarely","negatively","unusually","infrequently","uncommonly","irregularly","sporadically","sparsely","nowhere","narrowly","diffusely","skimpily","barely","sparely","hardly","insufficiently","inadequately","almost","scarcely","marginally","insignificantly","merely","meagerly","negligibly","scantily","slightly","weakly","unlikely","minimally","independent","lack","limited","less","null","little","negative","unable","unrelated","weaken","decreased","insignificant","instable","irrelevant","neither","depletion","reduction","down-regulation","abrogation","reduction","downregulation","suppression","Inhibition","inactivation","disruption","downmodulation","repression","absence","blockage","down-regulated","loss","decrease","blockade","suppressed","inhibits","inhibiting","inhibitors","downregulated","reduced","inhibited","knockdown","blocking","down-regulates","deleted","suppress","downregulating","Knockdown","reduce","reducing","diminished","blocked","lack","decreased","block","decreases","prevent","repressed","defects","blocks","Blocking","downmodulate","down-regulating","reduces","repress","attenuates","depressed")

# clean up sentences with R's regex-driven global substitute, gsub():
sentence = gsub('[[:punct:]]', '', sentence)
#sentence = gsub('[[:cntrl:]]', '', sentence)
#sentence = gsub('\\d+', '', sentence)
# and convert to lower case:
sentence = tolower(sentence)

# split into words. str_split is in the stringr package
word.list = str_split(sentence, '\\s+')
# sometimes a list() is one level of hierarchy too much
words = unlist(word.list)
stopwords_regex = paste(stopwords('en'), collapse = '\\b|\\b')
stopwords_regex = paste0('\\b', stopwords_regex, '\\b')
s = stringr::str_replace_all(words, stopwords_regex, ' ')
s
sent_token_annotator <- Maxent_Sent_Token_Annotator()
word_token_annotator <- Maxent_Word_Token_Annotator()
parse_annotator <- Parse_Annotator() 
a <- annotate(s, list(sent_token_annotator, word_token_annotator))
p <- parse_annotator(s, a) 
ptexts <- sapply(p$features, '[[', "parse") 
ptrees <- lapply(ptexts, Tree_parse) 
ptrees



if (!require("pacman")) install.packages("pacman")

pacman::p_load(parser, magrittr)


get_phrase_type_regex(ptext, "VP") %>%
  take() %>%
  get_leaves()
negative<-intersect(documents,neg)
negative



if (!require("pacman")) install.packages("pacman")

pacman::p_load(parser, magrittr)

x <- 'Coronary angiography holds a central role in the diagnosis of coronary heart disease. We studied temporal trends in referral patterns 2000-09.We identified 156 496 first-time coronary angiographies in nationwide registries. Trends were analyzed in 2-year intervals. Numbers of acute and elective procedures increased. Mean age increased from 61.8 to 63.9 years and the proportion of females increased from 33 to 37%). An increase in the number of patients with prior chronic heart failure, cerebrovascular disease, diabetes, and arrhythmias  was observed. The proportion of acute patients examined the same day as hospitalized increased from 56.6 to 83.1%. Odds ratios (95% confidence interval) for treatment with statins, RAS-inhibitors, and acetylsalicylic acid at the time of coronary angiography increased.'

get_phrase_type_regex(ptexts, "VP") %>%
  take() %>%
  get_leaves()


library("openNLP")

#x <- "Gulf Applied Technologies Inc said it sold its subsidiaries engaged in pipeline and terminal operations for 12.2 mln dlrs. The company said the sale is subject to certain post closing adjustments, which it did not explain. Reuter."

acqTag <- tagPOS(x)

sapply(strsplit(acqTag,"[[:punct:]]*/VB.?"),function(x) sub("(^.*\\s)(\\w+$)", "\\2", x))



