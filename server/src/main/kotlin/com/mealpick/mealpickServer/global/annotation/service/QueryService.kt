package com.mealpick.mealpickServer.global.annotation.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service()
@Transactional(readOnly = true)
annotation class QueryService {}