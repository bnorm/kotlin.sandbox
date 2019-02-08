workflow "main" {
  on = "push"
  resolves = ["Build"]
}

action "Build" {
  uses = "docker://openjdk:8"
  runs = "./gradlew build"
}
