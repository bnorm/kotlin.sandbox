workflow "main" {
  on = "push"
  resolves = ["Build"]
}

action "Build" {
  uses = "library/openjdk@8-jdk-stretch"
  runs = "./gradlew build"
}
