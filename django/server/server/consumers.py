import asyncio
import json
import random

from channels.consumer import AsyncConsumer
from channels.exceptions import StopConsumer


class GetCryptoCoinRate(AsyncConsumer):
   async  def websocket_connect(self,event):
        await self.send({
            'type':'websocket.accept'
        })

   async  def websocket_disconnect(self,event):
    try:
        if self.cryptoLoop.isRunning:
            self.cryptoLoop.cancel()
    except:
        pass   
    raise StopConsumer()



   async def rateLoop(self):
        self.cryptoLoop.isRunning = True
        while True:
            await asyncio.sleep(1)
            cryptoCoin={
            "DOT":random.uniform(1.5, 3.5),
            "BNB":random.uniform(30.5, 75.5),
            "XRP":random.uniform(40.5, 55.5),
            }
            await self.send({
                'type':'websocket.send',
                'text':json.dumps(cryptoCoin)
            })


            
   async  def websocket_receive(self,event):
        
        self.cryptoLoop = asyncio.create_task(self.rateLoop()) 