#!/bin/bash
if [ $1 = "boryi.news.business.sohu" ] || [ $1 = "boryi.news.business" ] || [ $1 = "boryi.news" ] || [ $1 = "boryi" ] || [ $1 = "all" ]
then
  cd /com/source/boryi/bots/news/business/com.boryi.bots.news.business.sohu/
  mv dist/ 11002-sohu/
  cd 11002-sohu/
  cp -R ../src/regex/ ./
  cp -R ../src/config/ ./
  cp -R ../src/sql/ ./
  echo 'sql_db_usr, sql_core_tbl, sql_core_pl, sql_core_data, sql_bot_tbl, sql_bot_pl, sql_bot_qa_data'> sql/readme
  mv config/run.sh ./
  chmod 755 run.sh
  cd ../
  rar a -r com.boryi.bots.news.business.sohu.rar 11002-sohu/
  mv com.boryi.bots.news.business.sohu.rar /com/source/boryi/bots/
  rm -rf 11002-sohu/
  rm -rf build/
  cd /com/source/boryi/bots/
fi
