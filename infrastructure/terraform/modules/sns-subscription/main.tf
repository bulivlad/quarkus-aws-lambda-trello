resource "aws_sns_topic_subscription" "trello-cards-sns" {
  topic_arn = "${var.topic_arn}"
  protocol  = "${var.subscription_protocol}"
  endpoint  = "${var.subscription_endpoint_arn}"
}