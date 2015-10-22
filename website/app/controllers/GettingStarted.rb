require 'cassandra'

cluster = Cassandra.connect

keyspace = 'demo'
session  = cluster.connect(keyspace)

session.execute("INSERT INTO users (lastname, age, city, email, firstname) VALUES ('Jones', 35, 'Austin', 'bob@example.com', 'Bob')")

session.execute("SELECT firstname, age FROM users WHERE lastname='Jones'").each do |row|
  p row
end


session.execute("UPDATE users SET age = 36 WHERE lastname = 'Jones'")

session.execute("SELECT firstname, age FROM users WHERE lastname='Jones'").each do |row|
  p row
end


session.execute("DELETE FROM users WHERE lastname = 'Jones'")

session.execute("SELECT * FROM users").each do |row|
  p row
end