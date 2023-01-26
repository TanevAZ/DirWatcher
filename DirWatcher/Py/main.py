import os
import time
import datetime

dir = 'PathToScript'
logpath = "PathToFileLog"

log = open(logpath, "a")

editTimes = {}

while True:
  for filename in os.listdir(dir):
    timecount = os.path.getmtime(os.path.join(dir, filename))

    if timecount > editTimes.get(filename, 0):
      print(f'[{time.ctime(timecount)}] : {filename} was modified.')

      now = datetime.datetime.now()

      formattedDate = now.strftime('%d/%m/%Y')

      log.write(f'[{time.ctime(timecount)} | {formattedDate}] : {filename} was modified.\n')

      editTimes[filename] = timecount

  time.sleep(1)
