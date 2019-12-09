module "trello-cards-sns" {
  source        = "../../modules/sns"
  name          = "trello-cards-sns"
}

module "trello-cards-processor-sns" {
  source        = "../../modules/sns"
  name          = "trello-cards-processor-sns"
}