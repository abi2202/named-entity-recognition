if (!require("pacman")) install.packages("pacman")

pacman::p_load(parser, magrittr)
get_phrase_type_regex(ptext, "VP") %>%
  take() %>%
  get_phrase_type_regex("NP") %>%
  take() %>%
  get_leaves()




