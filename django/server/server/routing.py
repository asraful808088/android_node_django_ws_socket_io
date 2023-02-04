from django.urls import path

from .consumers import GetCryptoCoinRate

urls_patterns=[
    path('ws/crypto',GetCryptoCoinRate.as_asgi())
]