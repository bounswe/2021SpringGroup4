import psycopg2
from psycopg2 import sql
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT # <-- ADD THIS LINE

con = psycopg2.connect(dbname='postgres',
      user='posgres', host='localhost',
      password='group4')

con.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)

cur = con.cursor()

cur.execute(sql.SQL("CREATE DATABASE {}").format(
        sql.Identifier('postgres'))
    )