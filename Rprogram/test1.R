library(NLP)
library(openNLP)

x <- 'Coronary angiography holds a central role in the diagnosis of coronary heart disease. We studied temporal trends in referral patterns 2000-09.We identified 156 496 first-time coronary angiographies in nationwide registries. Trends were analyzed in 2-year intervals. Numbers of acute and elective procedures increased. Mean age increased from 61.8 to 63.9 years and the proportion of females increased from 33 to 37%). An increase in the number of patients with prior chronic heart failure, cerebrovascular disease, diabetes, and arrhythmias  was observed. The proportion of acute patients examined the same day as hospitalized increased from 56.6 to 83.1%. Odds ratios (95% confidence interval) for treatment with statins, RAS-inhibitors, and acetylsalicylic acid at the time of coronary angiography increased.'
s <- as.String(x)

## Annotators
sent_token_annotator <- Maxent_Sent_Token_Annotator()
word_token_annotator <- Maxent_Word_Token_Annotator()
parse_annotator <- Parse_Annotator()

a2 <- annotate(s, list(sent_token_annotator, word_token_annotator))
p <- parse_annotator(s, a2)
ptext <- sapply(p$features, `[[`, "parse")
ptext
Tree_parse(ptext)

