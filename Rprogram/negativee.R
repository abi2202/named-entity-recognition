sent <- 'seldom Coronary angiography holds a central role in unusually the diagnosis of coronary heart disease. We studied temporal trends in referral patterns 2000-09.We identified 156 496 first-time coronary angiographies in nationwide registries. Trends were analyzed in 2-year intervals. Numbers of acute and elective procedures increased. Mean age increased from 61.8 to 63.9 years and the proportion of females increased from 33 to 37%). An increase in the number of patients with prior chronic heart failure, cerebrovascular disease, diabetes, and arrhythmias  was observed. The proportion of acute patients examined the same day as hospitalized increased from 56.6 to 83.1%. Odds ratios (95% confidence interval) for treatment with statins, RAS-inhibitors, and acetylsalicylic acid at the time of coronary angiography increased.'
sent


#end
neg = c("not","abnormal","seldom","rarely","negatively","unusually","infrequently","uncommonly","irregularly","sporadically","sparsely","nowhere","narrowly","diffusely","skimpily","barely","sparely","hardly","insufficiently","inadequately","almost","scarcely","marginally","insignificantly","merely","meagerly","negligibly","scantily","slightly","weakly","unlikely","minimally","independent","lack","limited","less","null","little","negative","unable","unrelated","weaken","decreased","insignificant","instable","irrelevant","neither","depletion","reduction","down-regulation","abrogation","reduction","downregulation","suppression","Inhibition","inactivation","disruption","downmodulation","repression","absence","blockage","down-regulated","loss","decrease","blockade","suppressed","inhibits","inhibiting","inhibitors","downregulated","reduced","inhibited","knockdown","blocking","down-regulates","deleted","suppress","downregulating","Knockdown","reduce","reducing","diminished","blocked","lack","decreased","block","decreases","prevent","repressed","defects","blocks","Blocking","downmodulate","down-regulating","reduces","repress","attenuates","depressed")




n = 0
for (i in 1:length(neg)) {
  if (grepl(neg[i],sent,ignore.case=T) == TRUE) 
    
  n = n + 1  
}


print(n)




sent1 = data.frame(Sentences=c("Originally discovered in the early 1990s [1], microRNAs (miRNAs) are small (18-24 nt long) RNAs that regulate gene expression mainly at the post-transcriptional level [2], [3]. Following their transcription, a miRNA processing complex consisting of the RNase Drosha and the cofactor DGCR8 cleaves the so-called pri-miRNAs. Thereby, the precursor miRNA (pre-miRNA) is built, which is characterized by a stem-loom structure [4], [5]. The pre-miRNA is rapidly exported from the nucleus into the cytoplasm, and is then cleaved by the enzyme Dicer to produce a short duplex molecule of mature miRNA. Finally, mature miRNAs are loaded into the RNA-induced silencing complex (RISC) where they are bound by a member of the Argonaute (AGO) family of RNA-binding proteins. The RISC complex can then bind to mRNAs that bear sequences which are complementary to the respective miRNA, which can either lead to degradation or to inhibition of translation of the mRNA, respectively [2]. Although miRNAs represent only a small fraction of the total cellular RNA, the copy numbers of mature miRNAs often exceed the copy numbers of their complementary mRNA, thus allowing a tight regulation of the respective complementary mRNA expression [6]. Many if not most miRNA sequences discovered so far are accessible through the database miRBase (http://www.mirbase.org), which is therefore a valuable resource for miRNA expression studies [7], [8]. Besides the miRNAs annotated in miRBase, different studies report evidence for even more miRNAs [9], [10]. Especially in the light of significant bias in high-throughput techniques such as next generation sequencing (NGS), respective novel miRNA candidates however, deserve further experimental validation [11].

                               Due to their pivotal function as regulators of gene expression, miRNAs play a critical role not only during physiological but also in pathological processes [12]. De-regulated expression of miRNAs will directly impact expression of their target mRNAs, which can therefore be indicative but also causative for a given disease. In fact, de-regulated miRNA expression has been correlated to many human diseases, including various cancers, cardiovascular diseases, neurological disorders, and others [13]. Although examples exist where the deregulated expression of a single miRNA is indicative of a physiological or pathological state, the simultaneous analysis of the expression of multiple miRNA usually provides a better sensitivity and specificity to detect the respective state. Certain miRNAs, e.g. miR-144, have been shown to be dys-regulated in a wide variety of different diseases, including cancer- and non-cancer pathologies [14]. Therefore a large part of candidate miRNA tests aims at measuring panels or signatures consisting of several miRNAs simultaneously rather than single miRNAs alone. Despite substantial advances in miRNA-related disease research, the applicability in clinical research has not finally been demonstrated [15]."))
words = (str_split(unlist(sent1$Sentences)," "))
tmp <- data.frame()
tmn <- data.frame()
for (m in 1:length(neg)){
  if (words[[i]][j] == neg[m]) { 
    print(paste(i,words[[i]][j],-1))
    tmn <- cbind(i,words[[i]][j],-1)
    tmp <- rbind(tmp,tmn)
  }
}  
}
}

print(tmp)
sent1$Sentences <- as.character(sent1$Sentences)
List <- strsplit(sent1$Sentences, " ")
a <- data.frame(Id=rep(sent1$user, sapply(List, length)),    Words=unlist(List))
a$Words <- as.character(a$Words)
a[a$Words %in% pos1,]