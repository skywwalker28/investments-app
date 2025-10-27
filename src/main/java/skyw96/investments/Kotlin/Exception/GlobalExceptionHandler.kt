package skyw96.investments.Kotlin.Exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    data class CustomErrorResponse(
        val error: String,
        val message: String?,
        val timestamp: LocalDateTime,
        val status: Int
    )

    @ExceptionHandler(InsufficientFundsException::class)
    fun InsufficientFundsException(ex: InsufficientFundsException): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            error = "INSUFFICIENT_FUNDS",
            message = ex.message,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(InsufficientStocksException::class)
    fun InsufficientStocks(ex: InsufficientStocksException): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            error = "INSUFFICIENT_STOCKS",
            message = ex.message,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(AccountNotFoundException::class)
    fun AccountNotFoundException(ex: AccountNotFoundException): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            error = "ACCOUNT_NOT_FOUND",
            message = ex.message,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(StockNotFoundException::class)
    fun StockNotFoundException(ex: StockNotFoundException): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            error = "STOCK_NOT_FOUND",
            message = ex.message,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(StockAlreadyExistException::class)
    fun StockNotFoundException(ex: StockAlreadyExistException): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            error = "STOCK_ALREADY_EXIST",
            message = ex.message,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(EmailAlreadyExistException::class)
    fun EmailAlreadyExistException(ex: EmailAlreadyExistException) : ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            error = "EMAIL_ALREADY_EXIST",
            message = ex.message,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(EmailNotFoundException::class)
    fun UserNotFoundException(ex: EmailNotFoundException) : ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            error = "USER_NOT_FOUND",
            message = ex.message,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun AccessDeniedException(ex: AccessDeniedException) : ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            error = "ACCESS_DENIED",
            message = ex.message,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}
