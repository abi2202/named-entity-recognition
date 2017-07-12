## Make a graph from Tree_parse result
parse2graph <- function(ptext, leaf.color='chartreuse4', label.color='blue4',
                        title=NULL, cex.main=.9, ...) {
  stopifnot(require(NLP) && require(igraph))
  
  ## Replace words with unique versions
  ms <- gregexpr("[^() ]+", ptext)                                      # just ignoring spaces and brackets?
  words <- regmatches(ptext, ms)[[1]]                                   # just words
  regmatches(ptext, ms) <- list(paste0(words, seq.int(length(words))))  # add id to words
  
  ## Going to construct an edgelist and pass that to igraph
  ## allocate here since we know the size (number of nodes - 1) and -1 more to exclude 'TOP'
  edgelist <- matrix('', nrow=length(words)-2, ncol=2)
  
  ## Function to fill in edgelist in place
  edgemaker <- (function() {
    i <- 0                                       # row counter
    g <- function(node) {                        # the recursive function
      if (inherits(node, "Tree")) {            # only recurse subtrees
        if ((val <- node$value) != 'TOP1') { # skip 'TOP' node (added '1' above)
          for (child in node$children) {
            childval <- if(inherits(child, "Tree")) child$value else child
            i <<- i+1
            edgelist[i,1:2] <<- c(val, childval)
          }
        }
        invisible(lapply(node$children, g))
      }
    }
  })()
  
  ## Create the edgelist from the parse tree
  edgemaker(Tree_parse(ptext))
  
  ## Make the graph, add options for coloring leaves separately
  g <- graph_from_edgelist(edgelist)
  vertex_attr(g, 'label.color') <- label.color  # non-leaf colors
  vertex_attr(g, 'label.color', V(g)[!degree(g, mode='out')]) <- leaf.color
  V(g)$label <- sub("\\d+", '', V(g)$name)      # remove the numbers for labels
  plot(g, layout=layout.reingold.tilford, ...)
  if (!missing(title)) title(title, cex.main=cex.main)
}


x <- x <- 'Coronary angiography holds a central role in the diagnosis of coronary heart disease. We studied temporal trends in referral patterns 2000-09.We identified 156 496 first-time coronary angiographies in nationwide registries. Trends were analyzed in 2-year intervals. Numbers of acute and elective procedures increased. Mean age increased from 61.8 to 63.9 years and the proportion of females increased from 33 to 37%). An increase in the number of patients with prior chronic heart failure, cerebrovascular disease, diabetes, and arrhythmias  was observed. The proportion of acute patients examined the same day as hospitalized increased from 56.6 to 83.1%. Odds ratios (95% confidence interval) for treatment with statins, RAS-inhibitors, and acetylsalicylic acid at the time of coronary angiography increased.'
s <- as.String(x)

## Annotators
sent_token_annotator <- Maxent_Sent_Token_Annotator()
word_token_annotator <- Maxent_Word_Token_Annotator()
parse_annotator <- Parse_Annotator()

a2 <- annotate(s, list(sent_token_annotator, word_token_annotator))
p <- parse_annotator(s, a2)
ptext <- sapply(p$features, `[[`, "parse")
Tree_parse(ptext)
ptext


library(igraph)
library(NLP)

z<-parse2graph(ptext,  # plus optional graphing parameters
            title = sprintf("'%s'", x), margin=-0.05,
            vertex.color=NA, vertex.frame.color=NA,
            vertex.label.font=2, vertex.label.cex=1.5, asp=0.5,
            edge.width=1.5, edge.color='black', edge.arrow.size=0)



